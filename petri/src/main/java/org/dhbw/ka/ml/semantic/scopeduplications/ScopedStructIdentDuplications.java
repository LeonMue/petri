package org.dhbw.ka.ml.semantic.scopeduplications;

import org.dhbw.ka.ml.generated.*;
import org.dhbw.ka.ml.semantic.scopeduplications.IdentifierAlreadyDeclaredException;

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
        return node.childrenAccept(this, data);
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
        return null;
    }

    @Override
    public Object visit(ASTPrimitiveType node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        final var symbolTable = (HashSet<String>) data;
        if (symbolTable.contains(node.getIdent())) {
            throw new IdentifierAlreadyDeclaredException(String.format(
                    "[line: %d, column: %d] The identifier %s is already declared in the same scope. Make sure an identifier is unique",
                    node.getBeginLine(),
                    node.getBeginColumn(),
                    node.getIdent()
            ));
        }
        symbolTable.add(node.getIdent());
        return null;
    }
}
