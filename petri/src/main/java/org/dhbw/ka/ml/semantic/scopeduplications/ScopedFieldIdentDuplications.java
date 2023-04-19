package org.dhbw.ka.ml.semantic.scopeduplications;

import org.dhbw.ka.ml.generated.*;

import java.util.HashSet;

public class ScopedFieldIdentDuplications implements PetriVisitor {
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
        final var nodeIdent = node.getIdent();
        final var symbolTable = (HashSet<String>) data;
        if (symbolTable.contains(nodeIdent.getIdent())) {
            throw new IdentifierAlreadyDeclaredException(String.format(
                    "[line: %d, column: %d] The identifier %s is already declared in the same scope. Make sure an identifier is unique",
                    nodeIdent.getBeginLine(),
                    nodeIdent.getBeginColumn(),
                    nodeIdent.getIdent()
            ));
        }

        symbolTable.add(nodeIdent.getIdent());
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
