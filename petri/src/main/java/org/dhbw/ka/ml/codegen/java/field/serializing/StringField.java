package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.Writer;

@Data
public class StringField implements PetriSerializable {

    @SneakyThrows
    @Override
    public void serializeDataOutput(String value, String dataOutput, Writer out) {
        out.write(String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriString.serialize(%s, %s);",
                value,
                dataOutput
        ));
    }

    @SneakyThrows
    @Override
    public String deserializeDataInput(String dataInput, Writer out) {
        out.write(String.format(
                "final String deserialized = org.dhbw.ka.ml.petrilib.serializing.primitives.PetriString.deserialize(%s);",
                dataInput
        ));
        return "deserialized";
    }

    @SneakyThrows
    @Override
    public void skip(String dataInput, Writer out) {
        out.write(String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriString.skip(%s);",
                dataInput
        ));
    }

}
