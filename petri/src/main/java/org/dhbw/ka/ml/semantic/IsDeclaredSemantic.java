package org.dhbw.ka.ml.semantic;

import org.dhbw.ka.ml.generated.*;

import java.util.HashSet;
import java.util.Set;

public class IsDeclaredSemantic {

    public static void check(ASTroot root) throws TypeNotDeclaredException {
        root.childrenAccept(new ComplexTypeVisitor(), null);
    }

    private static class ComplexTypeVisitor implements PetriVisitor {

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
            final var symbolTable = new HashSet<String>();
            node.childrenAccept(this, symbolTable);
            node.childrenAccept(new FieldTypeVisitor(), symbolTable);
            return null;
        }

        @Override
        public Object visit(ASTstruct node, Object data) {
            final var symbolTable = (Set<String>) data;
            final var structIdent = node.getIdent().getIdent();
            symbolTable.add(structIdent);
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

    private static class FieldTypeVisitor implements PetriVisitor {

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
        public Object visit(ASTfields node, Object data) {
            return node.childrenAccept(this, data);
        }

        @Override
        public Object visit(ASTfield node, Object data) {
            node.getType().jjtAccept(this, data);
            return null;
        }

        @Override
        public Object visit(ASTPrimitiveType node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            final var symbolTable = (Set<String>) data;
            if (!symbolTable.contains(node.getIdent())) {
                throw new TypeNotDeclaredException(String.format(
                        "[line: %d, column: %d] Type '%s' is not declared. Consider to declare types in order to use them.",
                        node.getBeginLine(),
                        node.getBeginColumn(),
                        node.getIdent()
                ));
            }
            return null;
        }

        @Override
        public Object visit(ASTList node, Object data) {
            node.getInnerType().jjtAccept(this, data);
            return null;
        }
    }

}
