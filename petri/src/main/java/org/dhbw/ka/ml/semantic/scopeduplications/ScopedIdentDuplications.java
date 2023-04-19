package org.dhbw.ka.ml.semantic.scopeduplications;

import org.dhbw.ka.ml.generated.*;

import java.util.HashSet;

public class ScopedIdentDuplications implements PetriVisitor {
    @Override
    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTroot node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTComplexTypes node, Object data) {
        final var structSymbolTable = new HashSet<String>();
        node.childrenAccept(new ScopedStructIdentDuplications(), structSymbolTable);
        node.childrenAccept(this, null);
        return null;
    }

    @Override
    public Object visit(ASTstruct node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        final var symbolTable = new HashSet<String>();
        return node.childrenAccept(new ScopedFieldIdentDuplications(), symbolTable);
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
}
