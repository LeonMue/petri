package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.generated.*;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class FieldDeclaration implements PetriVisitor {
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

        final var fieldTypeAnalysis = FieldTypeIdent.analyze(node.getType());
        final var fieldIdent = node.getIdent().getIdent();
        try {
            this.out.write(String.format(
                    "private %s %s;",
                    fieldTypeAnalysis.ident(),
                    fieldIdent
            ));
            if (!fieldTypeAnalysis.isNullable()) {
                this.out.write(String.format(
                        "private boolean has%s = false;",
                        JavaNameConventions.firstLetterUpperCase(fieldIdent)
                ));
            }
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

    @Override
    public Object visit(ASTList node, Object data) {
        return null;
    }
}
