package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.IOException;
import java.io.Writer;

@Data
public class DoubleField implements PetriSerializable {

    @Override
    public String serializeDataOutput(String value, String dataOutput) {
        return String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriDouble.serialize(%s, %s)",
                value,
                dataOutput
        );
    }

    @Override
    public String deserializeDataInput(String dataInput) {
        return String.format(
                "org.dhbw.ka.ml.petrilib.serializing.primitives.PetriDouble.deserialize(%s)",
                dataInput
        );
    }
}
