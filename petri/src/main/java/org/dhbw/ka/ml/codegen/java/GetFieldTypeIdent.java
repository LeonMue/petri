package org.dhbw.ka.ml.codegen.java;

import org.dhbw.ka.ml.generated.*;

import java.util.function.Function;

public class GetFieldTypeIdent {

    public static String getFieldTypeIdent(SimpleNode typeNode, Function<String, String> mapper) {
        final var internalVisitor = new InternalVisitor();
        typeNode.jjtAccept(internalVisitor, mapper);

        return internalVisitor.identResult.toString();
    }

    private static class InternalVisitor implements PetriVisitor {

        private static final PrimitivePetriTypeToPrimitiveJavaTypeMapper PRIMITIVE_PETRI_TYPE_TO_PRIMITIVE_JAVA_TYPE_MAPPER = new PrimitivePetriTypeToPrimitiveJavaTypeMapper();

        private static final PrimitivePetriTypeToComplexJavaTypeMapper PRIMITIVE_PETRI_TYPE_TO_COMPLEX_JAVA_TYPE_MAPPER = new PrimitivePetriTypeToComplexJavaTypeMapper();

        private final StringBuilder identResult = new StringBuilder();
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
            final var typeMapper = (Function<String, String>) data;
            final var type = typeMapper.apply(node.getType());
            this.identResult.append(type);
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            this.identResult.append(node.getIdent());
            return null;
        }

        @Override
        public Object visit(ASTList node, Object data) {
            this.identResult.append("List<");
            node.getInnerType().jjtAccept(this, PRIMITIVE_PETRI_TYPE_TO_COMPLEX_JAVA_TYPE_MAPPER);
            this.identResult.append(">");
            return null;
        }
    }

}
