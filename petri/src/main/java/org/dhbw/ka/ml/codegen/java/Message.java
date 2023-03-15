package org.dhbw.ka.ml.codegen.java;

import lombok.Data;
import org.dhbw.ka.ml.codegen.java.field.serializing.*;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.*;
import java.util.List;

@Data
public class Message {

    private final String packageName;
    private final String name;

    private final LinePrinter out;
    private final List<ASTfield> fields;

    private final PetriTypeToPetriSerializableMapper petriTypeToPetriSerializableMapper;

    private final PetriTypeToJavaTypeMapper petriTypeToJavaTypeMapper;

    public void generate() throws IOException {
        this.thisPackage();
        this.imports();
        this.out.write("public class %s {", this.name);
        for (final var field : this.fields) {
            this.memberField(field);
        }
        this.methodSerialize();
        this.methodDeserialize();
        for (final var field : this.fields) {
            this.getterFor(field);
            this.setterFor(field);
        }
        this.out.write("}");  // class
    }

    private void thisPackage() throws IOException {
        this.out.write("package %s;", this.packageName);
    }

    private void imports() throws IOException {
        this.out.write("import java.io.*;");
    }

    private void memberField(ASTfield field) throws IOException {
        this.out.write(
                "protected %s %s;",
                this.petriTypeToJavaTypeMapper.apply(field.getTypeIdent()),
                field.getFieldIdent()
        );
    }

    private void methodSerialize() throws IOException {
        this.out.write("public byte[] serialize() {");
        this.out.write("final ByteArrayOutputStream result = new ByteArrayOutputStream();");
        this.out.write("try (final DataOutputStream out = new DataOutputStream(result)) {");
        this.out.write("byte[] fieldNumber;");
        for (final var field : this.fields) {
            this.out.write(
                    "fieldNumber = org.dhbw.ka.ml.petrilib.serializing.VarInt.serializeUnsigned(%d);",
                    field.getFieldNumber()
            );
            this.out.write("out.write(fieldNumber);");

            this.out.write(
                    "%s;",
                    this.petriTypeToPetriSerializableMapper
                            .apply(field.getTypeIdent())
                            .serializeDataOutput("this." + field.getFieldIdent(), "out")
            );
        }
        this.out.write("}"); // try
        this.out.write("catch (IOException e) {");
        this.out.write("throw new RuntimeException(e);");
        this.out.write("}");  // catch
        this.out.write("return result.toByteArray();");
        this.out.write("}"); // serialize
    }

    private void methodDeserialize() throws IOException {
        this.out.write("public void deserialize(byte[] message) throws org.dhbw.ka.ml.petrilib.serializing.ParseException {");
        this.out.write("ByteArrayInputStream messageStream = new ByteArrayInputStream(message);");
        this.out.write("try (DataInputStream in = new DataInputStream(messageStream)) {");
        this.out.write("int fieldNumber;");
        this.out.write("while (messageStream.available() > 0) {");

        this.out.write("fieldNumber = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(in);");

        this.out.write("switch (fieldNumber) {");
        for (var field : fields) {
            this.out.write("case(%d): {", field.getFieldNumber());
            this.out.write(
                    "this.%s = %s;",
                    field.getFieldIdent(),
                    this.petriTypeToPetriSerializableMapper.apply(field.getTypeIdent()).deserializeDataInput("in")
            );
            this.out.write("break;");
            this.out.write("}");  // case
        }
        this.out.write("}");  // switch
        this.out.write("}");  // while
        this.out.write("}");  // try
        this.out.write("catch (IOException e) {");
        this.out.write("throw new org.dhbw.ka.ml.petrilib.serializing.ParseException(\"Unable to parse message because of IOException. Sure your format is correct?\", e);");
        this.out.write("}");  // catch
        this.out.write("}");  // deserialize
    }

    private void getterFor(ASTfield field) throws IOException {
        this.out.write(
                "public %s get%s() { return this.%s; }",
                this.petriTypeToJavaTypeMapper.apply(field.getTypeIdent()),
                JavaNameConventions.firstLetterUpperCase(field.getFieldIdent()),
                field.getFieldIdent()
        );
    }

    private void setterFor(ASTfield field) throws IOException {
        this.out.write(
                "public void set%s(%s value) { this.%s = value; }",
                JavaNameConventions.firstLetterUpperCase(field.getFieldIdent()),
                this.petriTypeToJavaTypeMapper.apply(field.getTypeIdent()),
                field.getFieldIdent()
        );
    }

}
