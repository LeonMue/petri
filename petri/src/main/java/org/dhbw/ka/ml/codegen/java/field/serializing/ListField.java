package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dhbw.ka.ml.codegen.java.GetFieldTypeIdent;
import org.dhbw.ka.ml.codegen.java.PrimitivePetriTypeToComplexJavaTypeMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.Writer;

@RequiredArgsConstructor
public class ListField implements PetriSerializable {

    private static final String YY_DESERIALIZED_LIST = "yyDeserializedList";

    private static final String YY_LIST_LENGTH = "yyListLength";

    private static final String YY_ITERATION_VAR = "yyI";

    private static final PetriTypeToPetriSerializableMapper PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER = new PetriTypeToPetriSerializableMapper();

    private static final PrimitivePetriTypeToComplexJavaTypeMapper PRIMITIVE_PETRI_TYPE_TO_COMPLEX_JAVA_TYPE_MAPPER = new PrimitivePetriTypeToComplexJavaTypeMapper();

    private final ASTList listTree;

    @SneakyThrows
    @Override
    public void serializeDataOutput(String value, String dataOutput, Writer out) {
        this.listTree.jjtAccept(new SerializeVisitor(out, dataOutput, value), null);
    }

    @SneakyThrows
    @Override
    public String deserializeDataInput(String dataInput, Writer out) {
        out.write(String.format(
                "final int %s = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(%s);",
                YY_LIST_LENGTH,
                dataInput
        ));
        final var typeIdent = GetFieldTypeIdent.getFieldTypeIdent(this.listTree, PRIMITIVE_PETRI_TYPE_TO_COMPLEX_JAVA_TYPE_MAPPER);
        out.write(String.format(
                "final %s %s = new Array%s(%s);",
                typeIdent,
                YY_DESERIALIZED_LIST,
                typeIdent,
                YY_LIST_LENGTH
        ));
        out.write(String.format(
                "for (int %s = 0; %s < %s; %s++) {",
                YY_ITERATION_VAR,
                YY_ITERATION_VAR,
                YY_LIST_LENGTH,
                YY_ITERATION_VAR
        ));
        this.listTree.getInnerType().jjtAccept(new DeserializeVisitor(dataInput, YY_DESERIALIZED_LIST, out), null);
        out.write("}");  // for
        return YY_DESERIALIZED_LIST;
    }

    @SneakyThrows
    @Override
    public void skip(String dataInput, Writer out) {
        out.write(String.format(
                "final int %s = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(%s);",
                YY_LIST_LENGTH,
                dataInput
        ));
        out.write(String.format(
                "for (int %s = 0; %s < %s; %s++) {",
                YY_ITERATION_VAR,
                YY_ITERATION_VAR,
                YY_LIST_LENGTH,
                YY_ITERATION_VAR
        ));
        this.listTree.getInnerType().jjtAccept(new SkipVisitor(dataInput, out), null);
        out.write("}");  // for
    }

    private class SerializeVisitor implements PetriVisitor {

        private final Writer out;

        private final String dataOutput;

        private String value;

        private int recursiveListCount = 0;

        public SerializeVisitor(Writer out, String dataOutput, String value) {
            this.out = out;
            this.dataOutput = dataOutput;
            this.value = value;
        }

        private static final String YY_ELEMENT = "yyElement";

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
            PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node).serializeDataOutput(
                    this.value,
                    this.dataOutput,
                    this.out
            );
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node).serializeDataOutput(
                    this.value,
                    this.dataOutput,
                    this.out
            );
            return null;
        }

        @SneakyThrows
        @Override
        public Object visit(ASTList node, Object data) {
            this.out.write(String.format(
                    "%s.write(org.dhbw.ka.ml.petrilib.serializing.VarInt.serializeUnsigned(%s.size()));",
                    this.dataOutput,
                    this.value
            ));
            final var assignedElemInForLoop = String.format(
                    "%s%d",
                    YY_ELEMENT,
                    this.recursiveListCount
            );
            this.out.write(String.format(
                    "for (%s %s : %s) {",
                    GetFieldTypeIdent.getFieldTypeIdent(node.getInnerType(), PRIMITIVE_PETRI_TYPE_TO_COMPLEX_JAVA_TYPE_MAPPER),
                    assignedElemInForLoop,
                    this.value
            ));
            this.out.write(String.format(
                    "if (%s == null) {",
                    assignedElemInForLoop
            ));
            this.out.write("throw new IllegalArgumentException(\"Elements of list must be not null\");");
            this.out.write("}");  // if
            this.value = assignedElemInForLoop;
            this.recursiveListCount++;
            node.getInnerType().jjtAccept(this, null);
            this.out.write("}");  // for
            return null;
        }
    }

    private class DeserializeVisitor implements PetriVisitor {

        private final String dataInput;

        private String parentList;

        private final Writer out;

        private int recursiveListCounter = 0;

        public DeserializeVisitor(String dataInput, String parentList, Writer out) {
            this.dataInput = dataInput;
            this.parentList = parentList;
            this.out = out;
        }

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

        @SneakyThrows
        @Override
        public Object visit(ASTPrimitiveType node, Object data) {
            final var deserialized = PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node).deserializeDataInput(this.dataInput, this.out);
            this.out.write(String.format(
                    "%s.add(%s);",
                    this.parentList,
                    deserialized
            ));
            return null;
        }

        @SneakyThrows
        @Override
        public Object visit(ASTIdentifier node, Object data) {
            final var deserialized = PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node).deserializeDataInput(this.dataInput, this.out);
            this.out.write(String.format(
                    "%s.add(%s);",
                    this.parentList,
                    deserialized
            ));
            return null;
        }

        @SneakyThrows
        @Override
        public Object visit(ASTList node, Object data) {
            final var typeIdent = GetFieldTypeIdent.getFieldTypeIdent(node, PRIMITIVE_PETRI_TYPE_TO_COMPLEX_JAVA_TYPE_MAPPER);
            final var uniqueLengthVariable = String.format(
                    "%s%d",
                    YY_LIST_LENGTH,
                    this.recursiveListCounter
            );
            final var childListIdent = String.format(
                    "%s%d",
                    YY_DESERIALIZED_LIST,
                    this.recursiveListCounter
            );
            final var uniqueIterationVar = String.format(
                    "%s%d",
                    YY_ITERATION_VAR,
                    this.recursiveListCounter
            );
            out.write(String.format(
                    "final int %s = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(%s);",
                    uniqueLengthVariable,
                    dataInput
            ));
            out.write(String.format(
                    "final %s %s = new Array%s(%s);",
                    typeIdent,
                    childListIdent,
                    typeIdent,
                    uniqueLengthVariable
            ));

            out.write(String.format(
                    "%s.add(%s);",
                    this.parentList,
                    childListIdent
            ));

            out.write(String.format(
                    "for (int %s = 0; %s < %s; %s++) {",
                    uniqueIterationVar,
                    uniqueIterationVar,
                    uniqueLengthVariable,
                    uniqueIterationVar
            ));
            this.parentList = childListIdent;
            this.recursiveListCounter++;
            node.getInnerType().jjtAccept(this, null);
            out.write("}");  // for
            return null;
        }
    }

    private class SkipVisitor implements PetriVisitor {

        private final String dataInput;

        private final Writer out;

        private int recursiveListCounter = 0;

        public SkipVisitor(String dataInput, Writer out) {
            this.dataInput = dataInput;
            this.out = out;
        }

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

        @SneakyThrows
        @Override
        public Object visit(ASTPrimitiveType node, Object data) {
            PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node).skip(this.dataInput, this.out);
            return null;
        }

        @SneakyThrows
        @Override
        public Object visit(ASTIdentifier node, Object data) {
            PETRI_TYPE_TO_PETRI_SERIALIZABLE_MAPPER.apply(node).skip(this.dataInput, this.out);
            return null;
        }

        @SneakyThrows
        @Override
        public Object visit(ASTList node, Object data) {
            final var uniqueLengthVariable = String.format(
                    "%s%d",
                    YY_LIST_LENGTH,
                    this.recursiveListCounter
            );
            final var uniqueIterationVar = String.format(
                    "%s%d",
                    YY_ITERATION_VAR,
                    this.recursiveListCounter
            );
            out.write(String.format(
                    "final int %s = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(%s);",
                    uniqueLengthVariable,
                    dataInput
            ));

            out.write(String.format(
                    "for (int %s = 0; %s < %s; %s++) {",
                    uniqueIterationVar,
                    uniqueIterationVar,
                    uniqueLengthVariable,
                    uniqueIterationVar
            ));
            this.recursiveListCounter++;
            node.getInnerType().jjtAccept(this, null);
            out.write("}");  // for
            return null;
        }
    }
}
