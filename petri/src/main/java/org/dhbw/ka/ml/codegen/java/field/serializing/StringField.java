package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;

@Data
public class StringField implements PetriSerializable {

    @Override
    public String serializeDataOutput(String value, String dataOutput) {
        return String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriString.serialize(%s, %s)",
                value,
                dataOutput
        );
    }

    @Override
    public String deserializeDataInput(String dataInput) {
        return String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriString.deserialize(%s)",
                dataInput
        );
    }

    @Override
    public String skip(String dataInput) {
        return String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriString.skip(%s)",
                dataInput
        );
    }

}
