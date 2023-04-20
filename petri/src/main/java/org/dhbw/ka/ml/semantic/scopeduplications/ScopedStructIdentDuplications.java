package org.dhbw.ka.ml.semantic.scopeduplications;

import org.dhbw.ka.ml.generated.*;

import java.util.HashSet;

public class ScopedStructIdentDuplications implements PetriVisitor {
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
        final var nodeIdent = node.getIdent().getIdent();
        final var symbolTable = (HashSet<String>) data;
        if (symbolTable.contains(nodeIdent)) {
            throw new IdentifierAlreadyDeclaredException(String.format(
                    "[line: %d, column: %d] The identifier %s is already declared in the same scope. Make sure an identifier is unique",
                    node.getIdent().getBeginLine(),
                    node.getIdent().getBeginColumn(),
                    nodeIdent
            ));
        }
        symbolTable.add(nodeIdent);
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return null;
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

    @Override
    public Object visit(ASTList node, Object data) {
        return null;
    }
}
