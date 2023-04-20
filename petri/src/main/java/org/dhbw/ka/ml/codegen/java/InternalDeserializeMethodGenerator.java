package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dhbw.ka.ml.codegen.java.field.serializing.PetriTypeToPetriSerializableMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.Writer;

@RequiredArgsConstructor
public class InternalDeserializeMethodGenerator {

    private final Writer out;

    private static final String YY_RESULT_VALUE = "yyResultValue";

    private static final String YY_BEGIN_OFFSET = "yyBeginOffset";

    private static final String YY_LENGTH_OF_BIT_VECTOR = "yyLengthOfBitVector";

    @SneakyThrows
    public void generate(ASTstruct struct) {
        final var structIdent = struct.getIdent().getIdent();
        this.out.write(String.format(
                "static %s yyDeserialize(org.dhbw.ka.ml.petrilib.io.PetriReader petriReader, int length) throws IOException {",
                structIdent
        ));
        this.out.write(String.format(
                "final %s %s = new %s();",
                structIdent,
                YY_RESULT_VALUE,
                structIdent
        ));
        this.out.write(String.format(
                "final int %s = petriReader.getOffset();",
                YY_BEGIN_OFFSET
        ));

        this.out.write(String.format(
                "int %s = 1;",
                YY_LENGTH_OF_BIT_VECTOR
        ));

        this.out.write("while ((petriReader.readByte() & 0x80) != 0) {");
        this.out.write(String.format(
                "%s++;",
                YY_LENGTH_OF_BIT_VECTOR
        ));
        this.out.write("}");  // while

        struct.jjtAccept(new FieldDeserializationVisitor(), null);

        this.out.write(String.format(
                "petriReader.skipBytes((%s + length) - petriReader.getOffset());",
                YY_BEGIN_OFFSET
        ));

        this.out.write(String.format(
                "return %s;",
                YY_RESULT_VALUE
        ));
        this.out.write("}");  // deserialize
    }

    private class FieldDeserializationVisitor implements PetriVisitor {

        private int fieldCounter = 0;

        private boolean newBitField = true;

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
            node.childrenAccept(this, data);
            return null;
        }

        @Override
        public Object visit(ASTfields node, Object data) {
            node.childrenAccept(this, data);
            return null;
        }

        @SneakyThrows
        @Override
        public Object visit(ASTfield node, Object data) {

            if (this.fieldCounter != 0 && (this.fieldCounter % 7) == 0) {
                out.write(String.format(
                        "if (%s == %d) {",
                        YY_LENGTH_OF_BIT_VECTOR,
                        this.fieldCounter / 7
                ));
                out.write(String.format(
                        "return %s;",
                        YY_RESULT_VALUE
                ));
                out.write("}");  // if
            }
            out.write(String.format(
                        "if ((petriReader.getByteAt(%s + %d) & %d) != 0) {",
                        YY_BEGIN_OFFSET,
                        this.fieldCounter / 7,
                        1 << (this.fieldCounter % 7)
            ));
            final var deserializer = PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node.getType());
            if (node.isDeleted()) {
                deserializer.skip("petriReader", out);
            } else {
                final var deserialized = deserializer.deserializeDataInput("petriReader", out);
                out.write(String.format(
                        "%s.set%s(%s);",
                        YY_RESULT_VALUE,
                        JavaNameConventions.firstLetterUpperCase(node.getIdent().getIdent()),
                        deserialized
                ));
            }
            out.write("}");  // if
            this.fieldCounter++;
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
