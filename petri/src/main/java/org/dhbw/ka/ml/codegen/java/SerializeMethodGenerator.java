package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dhbw.ka.ml.codegen.java.field.serializing.PetriTypeToPetriSerializableMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.Writer;

@RequiredArgsConstructor
public class SerializeMethodGenerator {

    private final Writer out;

    private static final String YY_RESULT = "yyResult";

    private static final String YY_BIT_VECTOR = "yyBitVector";

    private static final String YY_SERIALIZED_FIELDS = "yySerializedFields";

    private static final int MAX_BITS_PER_VECTOR = 7;

    private static final String YY_DATA_OUTPUT = "yyDataOutput";

    private int fieldCounter;

    @SneakyThrows
    public void generate(ASTstruct struct) {
        this.out.write("public byte[] serialize() {");
        struct.jjtAccept(new FieldCounter(), null);
        this.out.write(String.format(
                "byte[] %s = new byte[%d];",
                YY_BIT_VECTOR,
                this.fieldCounter == 0 ? 1 : (int) Math.ceil(this.fieldCounter / (float) MAX_BITS_PER_VECTOR)
        ));
        this.out.write(String.format(
                "ByteArrayOutputStream %s = new ByteArrayOutputStream();",
                YY_SERIALIZED_FIELDS
        ));
        this.out.write(String.format(
                "try (final DataOutputStream %s = new DataOutputStream(%s)) {",
                YY_DATA_OUTPUT,
                YY_SERIALIZED_FIELDS
        ));
        struct.jjtAccept(new FieldVisitor(), null);
        this.out.write(String.format(
                "ByteArrayOutputStream %s = new ByteArrayOutputStream();",
                YY_RESULT
        ));
        this.out.write(String.format(
                "%s.writeBytes(%s);",
                YY_RESULT,
                YY_BIT_VECTOR
        ));
        this.out.write(String.format(
                "%s.writeTo(%s);",
                YY_SERIALIZED_FIELDS,
                YY_RESULT
        ));
        this.out.write(String.format(
                "return %s.toByteArray();",
                YY_RESULT
        ));
        this.out.write("}");  // try
        this.out.write("catch (IOException e) {");
        this.out.write("throw new RuntimeException(\"This should not happen...\", e);");
        this.out.write("}");  // catch
        this.out.write("}");  // serialize
    }

    private class FieldVisitor implements PetriVisitor {

        private int bitFieldOffset = 0;

        private int bitField = 0;

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
            if (!node.isDeleted()) {
                out.write(String.format(
                        "if (this.has%s()) {",
                        fieldIdentUppercase
                ));

                out.write(String.format(
                        "%s[%d] |= %d;",
                        YY_BIT_VECTOR,
                        this.bitField,
                        1 << this.bitFieldOffset
                ));

                PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER
                        .apply(node.getType())
                        .serializeDataOutput(fieldIdent, YY_DATA_OUTPUT, out);

                out.write("}");  // if
            }

            this.bitFieldOffset = (this.bitFieldOffset + 1) % MAX_BITS_PER_VECTOR;
            if (this.bitFieldOffset == 0) {
                out.write(String.format(
                        "%s[%d] |= 0x80;",
                        YY_BIT_VECTOR,
                        this.bitField
                ));
                this.bitField++;
            }
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

        @Override
        public Object visit(ASTList node, Object data) {
            return null;
        }
    }

    private class FieldCounter implements PetriVisitor {

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
            fieldCounter = node.jjtGetNumChildren();
            return null;
        }

        @Override
        public Object visit(ASTfield node, Object data) {
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

        @Override
        public Object visit(ASTList node, Object data) {
            return null;
        }
    }

}
