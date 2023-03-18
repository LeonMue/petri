package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.generated.*;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class Setter implements PetriVisitor {

    private final Writer out;
    private String typeIdent;
    private String fieldIdent;
    private boolean isNullable;

    private final PetriTypeToJavaTypeMapper petriTypeToJavaTypeMapper = new PetriTypeToJavaTypeMapper();

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
        if (node.isDeleted()) {
            return null;
        }

        node.childrenAccept(this, null);
        try {
            this.out.write(String.format(
                    "public void set%s(%s value) {",
                    JavaNameConventions.firstLetterUpperCase(this.fieldIdent),
                    this.typeIdent
            ));
            if (!this.isNullable) {
                this.out.write(String.format(
                        "this.has%s = true;",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                ));
            }
            this.out.write(String.format(
                    "this.%s = value;",
                    this.fieldIdent
            ));
            this.out.write("}");  // set
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
