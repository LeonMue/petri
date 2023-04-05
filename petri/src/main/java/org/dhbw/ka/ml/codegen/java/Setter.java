package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.generated.*;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class Setter implements PetriVisitor {

    private final Writer out;

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
        return null;
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        if (node.isDeleted()) {
            return null;
        }

        final var fieldTypeIdentAnalysis = FieldTypeIdent.analyze(node.getType());
        final var typeIdent = fieldTypeIdentAnalysis.ident();
        final var isNullable = fieldTypeIdentAnalysis.isNullable();
        final var fieldIdent = node.getIdent().getIdent();
        final var fieldIdentUppercase = JavaNameConventions.firstLetterUpperCase(fieldIdent);
        node.childrenAccept(this, null);
        try {
            this.out.write(String.format(
                    "public void set%s(%s value) {",
                    fieldIdentUppercase,
                    typeIdent
            ));
            if (!isNullable) {
                this.out.write(String.format(
                        "this.has%s = true;",
                        fieldIdentUppercase
                ));
            }
            this.out.write(String.format(
                    "this.%s = value;",
                    fieldIdent
            ));
            this.out.write("}");  // set
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
