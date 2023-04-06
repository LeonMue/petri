package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dhbw.ka.ml.codegen.java.field.serializing.PetriTypeToPetriSerializableMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.Writer;

@RequiredArgsConstructor
public class InternalDeserializeMethod {

    private final Writer out;

    @SneakyThrows
    public void generate(ASTstruct struct) {
        final var structIdent = struct.getIdent().getIdent();
        this.out.write(String.format(
                "static %s deserialize(org.dhbw.ka.ml.petrilib.io.ReadTrackingDataInput readTrackingIn, int length) throws org.dhbw.ka.ml.petrilib.serializing.ParseException {",
                structIdent
        ));
        this.out.write(String.format(
                "%s value = new %s();",
                structIdent,
                structIdent
        ));
        this.out.write("try {");
        this.out.write("int fieldNumber;");
        this.out.write("while (readTrackingIn.getBytesWasRead() < length) {");
        this.out.write("fieldNumber = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(readTrackingIn);");
        this.out.write("switch (fieldNumber) {");

        // cover all cases of field numbers
        struct.childrenAccept(new InternalVisitor(this.out), null);

        // If no cases got matched => unknown field => stop deserializing for this message
        this.out.write("default: {");
        this.out.write("readTrackingIn.skipBytes(length - readTrackingIn.getBytesWasRead());");
        this.out.write("return value;");
        this.out.write("}");  // default

        this.out.write("}");  // switch
        this.out.write("}");  // while
        this.out.write("}");  // try
        this.out.write("catch (IOException e) {");
        this.out.write("throw new org.dhbw.ka.ml.petrilib.serializing.ParseException(\"Unable to parse message because of IOException. Sure your format is correct?\", e);");
        this.out.write("}");  // catch
        this.out.write("return value;");
        this.out.write("}");  // deserialize
    }

        private record InternalVisitor(Writer out) implements PetriVisitor {

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
                return null;
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
                out.write(String.format(
                        "case (%d): {",
                        node.getFieldNumber()
                ));
                final var serializable = PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node.getType());
                if (node.isDeleted()) {
                    serializable.skip("readTrackingIn", out);
                } else {
                    final var deserialized = serializable.deserializeDataInput("readTrackingIn", out);
                    this.out.write(String.format(
                            "value.set%s(%s);",
                            fieldIdentUppercase,
                            deserialized
                    ));
                }
                this.out.write("break;");
                this.out.write("}");  // case
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
