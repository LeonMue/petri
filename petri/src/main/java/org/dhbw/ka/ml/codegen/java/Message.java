package org.dhbw.ka.ml.codegen.java;

import lombok.Data;
import org.dhbw.ka.ml.codegen.java.field.serializing.*;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

@Data
public class Message {

    private final String packageName;
    private final String name;
    private final Writer out;
    private final List<ASTfield> fields;

    private Map<String, String> fromPetriTypeToJavaType = Map.ofEntries(
            entry("bool", "boolean"),
            entry("int", "int"),
            entry("long", "long"),
            entry("float", "float"),
            entry("double", "double"),
            entry("string", "String")
    );

    public void generate() throws IOException {
        this.thisPackage();
        this.imports();
        this.out.write(String.format("public class %s {", this.name));
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
        this.out.write(String.format("package %s;", this.packageName));
    }

    private void imports() throws IOException {
        // TODO
        this.out.write("import com.clearspring.analytics.util.Varint;");
        this.out.write("import java.io.*;");
    }

    private void memberField(ASTfield field) throws IOException {
        this.out.write(String.format(
                "private %s %s;",
                fromPetriPrimitiveTypeToJavaType(field.getTypeIdent()),
                field.getFieldIdent()
        ));
    }

    private void methodSerialize() throws IOException {
        this.out.write("public byte[] serialize() throws IOException {");
        this.out.write("final var result = new ByteArrayOutputStream();");
        this.out.write("try (final var out = new DataOutputStream(result)) {");
        for (final var field : this.fields) {
            this.out.write(String.format(
                    "Varint.writeUnsignedVarInt(%d, out);",
                    field.getFieldNumber()
            ));
            /*switch (field.getTypeIdent()) {
                case "bool" -> this.out.write(String.format(
                        "out.writeBoolean(this.%s);",
                        field.getFieldIdent()
                ));
                case "int" -> this.out.write(String.format(
                        "Varint.writeSignedVarInt(this.%s, out);",
                        field.getFieldIdent()
                ));
                case "long" -> this.out.write(String.format(
                        "Varint.writeSignedVarLong(this.%s, out);",
                        field.getFieldIdent()
                ));
                case "float" -> this.out.write(String.format(
                        "out.writeFloat(this.%s);",
                        field.getFieldIdent()
                ));
                case "double" -> this.out.write(String.format(
                        "out.writeDouble(this.%s);",
                        field.getFieldIdent()
                ));
                case "string" -> this.out.write(String.format(
                        "Varint.writeUnsignedVarInt(this.%s.length(), out); out.writeBytes(this.%s);",
                        field.getFieldIdent(),
                        field.getFieldIdent()
                ));
            }*/
            switch (field.getTypeIdent()) {
                case "bool" -> new BoolField(field, this.out).serializeDataOutput("out");
                case "int" -> new IntField(field, this.out).serializeDataOutput("out");
                case "long" -> new LongField(field, this.out).serializeDataOutput("out");
                case "float" -> new FloatField(field, this.out).serializeDataOutput("out");
                case "double" -> new DoubleField(field, this.out).serializeDataOutput("out");
                case "string" -> new StringField(field, this.out).serializeDataOutput("out");
            }
        }
        this.out.write("}"); // try
        this.out.write(String.format(
                "return result.toByteArray();"
        ));
        this.out.write("}"); // serialize
    }

    private void methodDeserialize() throws IOException {
        this.out.write("public void deserialize(byte[] message) throws IOException {");
        this.out.write("ByteArrayInputStream messageStream = new ByteArrayInputStream(message);");
        this.out.write("DataInputStream in = new DataInputStream(messageStream);");
        this.out.write(String.format(
                "while (messageStream.available() > 0) {"
        ));
        this.out.write(String.format(
                "int fieldNumber = Varint.readUnsignedVarInt(in);"
        ));
        this.out.write("switch (fieldNumber) {");
        for (var field : fields) {
            this.out.write(String.format(
                    "case (%d): {",
                    field.getFieldNumber()
            ));
            switch (field.getTypeIdent()) {
                case "bool" -> new BoolField(field, this.out)       .deserializeDataInput("in");
                case "int" -> new IntField(field, this.out)         .deserializeDataInput("in");
                case "long" -> new LongField(field, this.out)       .deserializeDataInput("in");
                case "float" -> new FloatField(field, this.out)     .deserializeDataInput("in");
                case "double" -> new DoubleField(field, this.out)   .deserializeDataInput("in");
                case "string" -> new StringField(field, this.out)   .deserializeDataInput("in");
            }
            this.out.write("break;");
            this.out.write("}");  // case
        }
        this.out.write("}"); // switch
        this.out.write("}");  // while
        this.out.write("in.close();");
        this.out.write("}");  // deserialize
    }

    private void getterFor(ASTfield field) throws IOException {
        this.out.write(String.format(
                "public %s get%s() { return this.%s; }",
                fromPetriPrimitiveTypeToJavaType(field.getTypeIdent()),
                this.firstLetterUpperCaseName(field.getFieldIdent()),
                field.getFieldIdent()
        ));
    }

    private void setterFor(ASTfield field) throws IOException {
        this.out.write(String.format(
                "public void set%s(%s value) { this.%s = value; }",
                this.firstLetterUpperCaseName(field.getFieldIdent()),
                this.fromPetriPrimitiveTypeToJavaType(field.getTypeIdent()),
                field.getFieldIdent()
        ));
    }

    private String firstLetterUpperCaseName(String name) {
        final var firstLetterUpperCase = Character.toUpperCase(name.charAt(0));
        final var tail = name.substring(1);

        return String.format("%c%s", firstLetterUpperCase, tail);
    }

    private String fromPetriPrimitiveTypeToJavaType(String petriPrimitive) {
        return this.fromPetriTypeToJavaType.get(petriPrimitive);
    }

}
