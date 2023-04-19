package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.Writer;

@Data
public class IntField implements PetriSerializable {

    @SneakyThrows
    @Override
    public void serializeDataOutput(String value, String dataOutput, Writer out) {
        out.write(String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriInt.serialize(%s, %s);",
                value,
                dataOutput
        ));
    }

    @SneakyThrows
    @Override
    public String deserializeDataInput(String dataInput, Writer out) {
        out.write(String.format(
                "final int deserialized = org.dhbw.ka.ml.petrilib.serializing.primitives.PetriInt.deserialize(%s);",
                dataInput
        ));
        return "deserialized";
    }

    @SneakyThrows
    @Override
    public void skip(String dataInput, Writer out) {
        out.write(String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriInt.skip(%s);",
                dataInput
        ));
    }

}
