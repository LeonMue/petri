package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.Writer;

@RequiredArgsConstructor
public class ComplexObjectField implements PetriSerializable {

    private final String typeIdent;

    @SneakyThrows
    @Override
    public void serializeDataOutput(String value, String dataOutput, Writer out) {
        out.write(String.format(
                "final byte[] serialized = %s.serialize();",
                value
        ));
        out.write(String.format(
                "%s.write(serialized.length);",
                dataOutput
        ));
        out.write(String.format(
                "%s.write(serialized);",
                dataOutput
        ));
    }

    @SneakyThrows
    @Override
    public String deserializeDataInput(String dataInput, Writer out) {
        out.write(String.format(
                "final int serializedLength = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(%s);",
                dataInput
        ));
        out.write(String.format(
                "final %s deserialized = %s.yyDeserialize(%s, serializedLength);",
                this.typeIdent,
                this.typeIdent,
                dataInput
        ));
        return "deserialized";
    }

    @SneakyThrows
    @Override
    public void skip(String dataInput, Writer out) {
        out.write(String.format(
                "final int serializedLength = org.dhbw.ka.ml.petrilib.serializing.VarInt.deserializeUnsigned(%s);",
                dataInput
        ));
        out.write(String.format(
                "%s.skipBytes(serializedLength);",
                dataInput
        ));
    }
}
