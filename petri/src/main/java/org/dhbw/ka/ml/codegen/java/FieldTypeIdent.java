package org.dhbw.ka.ml.codegen.java;

import org.dhbw.ka.ml.generated.*;

public class FieldTypeIdent {

    public static Result analyze(SimpleNode typeIdent) {
        final var internalVisitor = new Visitor();
        typeIdent.jjtAccept(internalVisitor, null);
        return internalVisitor.result;
    }

    record Result(boolean isNullable, String ident) {
    }

    private static class Visitor implements PetriVisitor {
        private Result result;

        private static final PetriTypeToJavaTypeMapper petriTypeToJavaTypeMapper = new PetriTypeToJavaTypeMapper();

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
            return null;
        }

        @Override
        public Object visit(ASTPrimitiveType node, Object data) {
            final var isNullable = node.getType().equals("string");
            final var javaType = petriTypeToJavaTypeMapper.apply(node.getType());
            this.result = new Result(isNullable, javaType);
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            this.result = new Result(true, node.getIdent());
            return null;
        }
    }

}
