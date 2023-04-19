package org.dhbw.ka.ml.codegen.java.field.serializing;

import org.dhbw.ka.ml.generated.*;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class PetriTypeToPetriSerializableMapper implements Function<SimpleNode, PetriSerializable> {

    @Override
    public PetriSerializable apply(SimpleNode type) {
        final var internalVisitor = new InternalVisitor();
        type.jjtAccept(internalVisitor, null);
        return internalVisitor.result;
    }

    private static class InternalVisitor implements PetriVisitor {

        private static final Map<String, PetriSerializable> PRIMITIVE_TYPE_TO_PETRI_SERIALIZABLE_MAPPING = Map.ofEntries(
                entry("bool", new BoolField()),
                entry("int32", new IntField()),
                entry("int64", new LongField()),
                entry("float32", new FloatField()),
                entry("float64", new DoubleField()),
                entry("string", new StringField())
        );

        private PetriSerializable result;

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
            this.result = PRIMITIVE_TYPE_TO_PETRI_SERIALIZABLE_MAPPING.get(node.getType());
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            this.result = new ComplexObjectField(node.getIdent());
            return null;
        }
    }
}
