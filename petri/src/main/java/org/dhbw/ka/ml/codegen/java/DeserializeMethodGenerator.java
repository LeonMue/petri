package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dhbw.ka.ml.codegen.java.field.serializing.PetriTypeToPetriSerializableMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.*;

@RequiredArgsConstructor
public class DeserializeMethodGenerator {

    private final Writer out;

    private static final String YY_RESULT_VALUE = "yyResultValue";

    private static final String YY_SERIALIZED_BYTE_ARRAY = "serialized";

    private static final String YY_IN = "yyIn";

    private static final String YY_LENGTH_OF_BIT_VECTOR = "yyLengthOfBitVector";

    private static final int MAX_BITS_PER_BIT_VECTOR_FIELD = 7;


    @SneakyThrows
    public void generate(ASTstruct struct) {
        final var structIdent = struct.getIdent().getIdent();

        this.out.write(String.format(
                "public static %s deserialize(byte[] %s) throws org.dhbw.ka.ml.petrilib.serializing.ParseException {",
                struct.getIdent().getIdent(),
                YY_SERIALIZED_BYTE_ARRAY
        ));
        this.out.write(String.format(
                "final %s %s = new %s();",
                structIdent,
                YY_RESULT_VALUE,
                structIdent
        ));

        this.out.write(String.format(
                "try (final org.dhbw.ka.ml.petrilib.io.PetriReader %s = new org.dhbw.ka.ml.petrilib.io.PetriReader(%s)) {",
                YY_IN,
                YY_SERIALIZED_BYTE_ARRAY
        ));
        this.out.write(String.format(
                "int %s = 1;",
                YY_LENGTH_OF_BIT_VECTOR
        ));

        this.out.write(String.format(
                "while ((%s.readByte() & 0x80) != 0) {",
                YY_IN
        ));
        this.out.write(String.format(
                "%s++;",
                YY_LENGTH_OF_BIT_VECTOR
        ));
        this.out.write("}");  // while

        struct.jjtAccept(new GeneratorVisitor(), null);
        this.out.write("}");  // try
        this.out.write(String.format(
                "catch (IOException e) {"
        ));
        this.out.write("throw new org.dhbw.ka.ml.petrilib.serializing.ParseException(\"Unable to parse message because of IOException. Sure your format is correct?\", e);");
        this.out.write("}");  // catch
        this.out.write(String.format(
                "return %s;",
                YY_RESULT_VALUE
        ));
        this.out.write("}");  // deserialize
    }

    private class GeneratorVisitor implements PetriVisitor {

        private int fieldCount = 0;

        private static final PetriTypeToPetriSerializableMapper PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER = new PetriTypeToPetriSerializableMapper();

        @Override
        public Object visit(SimpleNode node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTroot node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTComplexTypes node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTstruct node, Object data) {
            return node.childrenAccept(this, data);
        }

        @Override
        public Object visit(ASTfields node, Object data) {
            return node.childrenAccept(this, data);
        }

        @SneakyThrows
        @Override
        public Object visit(ASTfield node, Object data) {
            final var fieldIdent = node.getIdent().getIdent();
            final var fieldIdentUppercase = JavaNameConventions.firstLetterUpperCase(fieldIdent);
            if (this.fieldCount != 0 && this.fieldCount % MAX_BITS_PER_BIT_VECTOR_FIELD == 0) {
                out.write(String.format(
                        "if (%s == %d) {",
                        YY_LENGTH_OF_BIT_VECTOR,
                        this.fieldCount / MAX_BITS_PER_BIT_VECTOR_FIELD
                ));
                out.write(String.format(
                        "return %s;",
                        YY_RESULT_VALUE
                ));
                out.write("}");  // if
            }
            out.write(String.format(
                    "if ((%s[%d] & %d) != 0) {",
                    YY_SERIALIZED_BYTE_ARRAY,
                    this.fieldCount / MAX_BITS_PER_BIT_VECTOR_FIELD,
                    1 << (this.fieldCount % MAX_BITS_PER_BIT_VECTOR_FIELD)
            ));
            final var petriSerializable = PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node.getType());
            if (node.isDeleted()) {
                petriSerializable.skip(YY_IN, out);
            } else {
                final var deserialized = petriSerializable.deserializeDataInput(YY_IN, out);
                out.write(String.format(
                        "%s.set%s(%s);",
                        YY_RESULT_VALUE,
                        fieldIdentUppercase,
                        deserialized
                ));
            }
            out.write("}");  // if
            this.fieldCount++;
            return null;
        }

        @Override
        public Object visit(ASTPrimitiveType node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            return null;
        }
    }
}
