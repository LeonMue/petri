package org.dhbw.ka.ml.codegen.java.field.serializing;

import java.io.IOException;

public interface PetriSerializable {

    String serializeDataOutput(String value, String dataOutput);

    String deserializeDataInput(String dataInput);

}
