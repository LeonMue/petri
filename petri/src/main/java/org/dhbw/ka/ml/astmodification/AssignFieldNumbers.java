package org.dhbw.ka.ml.astmodification;

import org.dhbw.ka.ml.generated.*;

public class AssignFieldNumbers implements PetriVisitor {

    private int fieldNumber;

    @Override
    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTroot node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTComplexTypes node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTstruct node, Object data) {
        this.fieldNumber = 0;
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        node.setFieldNumber(this.fieldNumber);
        this.fieldNumber++;
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
