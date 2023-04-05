package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.generated.*;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class Getter implements PetriVisitor {

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
        final var fieldType = fieldTypeIdentAnalysis.ident();
        final var isNullable = fieldTypeIdentAnalysis.isNullable();
        final var fieldIdent = node.getIdent().getIdent();
        final var fieldIdentUppercase = JavaNameConventions.firstLetterUpperCase(fieldIdent);
        try {
            this.out.write(String.format(
                    "public %s get%s() {",
                    fieldType,
                    fieldIdentUppercase
            ));
            this.out.write(String.format(
                    "return this.%s;",
                    fieldIdent
            ));
            this.out.write("}");  // get
            this.out.write(String.format(
                    "public boolean has%s() {",
                    fieldIdentUppercase
            ));
            if (!isNullable) {
                this.out.write(String.format(
                        "return this.has%s;",
                        fieldIdentUppercase
                ));
            } else {
                this.out.write(String.format(
                        "return this.%s != null;",
                        fieldIdent
                ));
            }
            this.out.write("}");  // hasFieldIdent
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
