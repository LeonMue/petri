package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.codegen.java.field.serializing.JavaTypeToPetriSerializableMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class MethodSerialize implements PetriVisitor {

    private final Writer out;

    private final JavaTypeToPetriSerializableMapper javaTypeToPetriSerializableMapper = new JavaTypeToPetriSerializableMapper();

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
        try {
            this.out.write("public byte[] serialize() {");
            this.out.write("final ByteArrayOutputStream result = new ByteArrayOutputStream();");
            this.out.write("try (final DataOutputStream out = new DataOutputStream(result)) {");
            this.out.write("byte[] fieldNumber;");
            node.childrenAccept(this, null);
            this.out.write("}");  // try
            this.out.write("catch (IOException e) {");
            this.out.write("throw new RuntimeException(e);");
            this.out.write("}");  // catch
            this.out.write("return result.toByteArray();");
            this.out.write("}");  // serialize
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        if (node.isDeleted()) {
            return null;
        }

        final var typeIdent = FieldTypeIdent.analyze(node.getType()).ident();
        final var fieldIdent = node.getIdent().getIdent();
        final var fieldIdentUppercase = JavaNameConventions.firstLetterUpperCase(fieldIdent);
        try {
            this.out.write(String.format(
                    "if (this.has%s()) {",
                    fieldIdentUppercase
            ));
            this.out.write(String.format(
                    "fieldNumber = org.dhbw.ka.ml.petrilib.serializing.VarInt.serializeUnsigned(%d);",
                    node.getFieldNumber()
            ));
            this.out.write("out.write(fieldNumber);");
            this.out.write(String.format(
                    "%s;",
                    this.javaTypeToPetriSerializableMapper
                            .apply(typeIdent)
                            .serializeDataOutput("this." + fieldIdent, "out")
            ));
            this.out.write("}");  // if
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
