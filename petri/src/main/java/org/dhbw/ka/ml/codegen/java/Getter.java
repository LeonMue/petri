package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.generated.*;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class Getter implements PetriVisitor {

    private final Writer out;
    private final PetriTypeToJavaTypeMapper petriTypeToJavaTypeMapper = new PetriTypeToJavaTypeMapper();

    private String typeIdent;
    private String fieldIdent;
    private boolean isNullable;

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
    public Object visit(ASTParentIdentifier node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        node.childrenAccept(this, data);
        final var fieldIdentUppercase = JavaNameConventions.firstLetterUpperCase(this.fieldIdent);
        try {
            this.out.write(String.format(
                    "public %s get%s() {",
                    this.typeIdent,
                    fieldIdentUppercase
            ));
            this.out.write(String.format(
                    "return this.%s;",
                    this.fieldIdent
            ));
            this.out.write("}");  // get
            this.out.write(String.format(
                    "public boolean has%s() {",
                    fieldIdentUppercase
            ));
            if (!this.isNullable) {
                this.out.write(String.format(
                        "return this.has%s;",
                        fieldIdentUppercase
                ));
            } else {
                this.out.write(String.format(
                        "return this.%s != null;",
                        this.fieldIdent
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
        this.typeIdent = this.petriTypeToJavaTypeMapper.apply(node.getType());
        this.isNullable = node.getType().equals("string");
        return null;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        this.fieldIdent = node.getIdent();
        return null;
    }
}
