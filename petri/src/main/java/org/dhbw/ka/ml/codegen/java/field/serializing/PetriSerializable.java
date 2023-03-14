package org.dhbw.ka.ml.codegen.java.field.serializing;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;

public interface PetriSerializable {

    void serializeDataOutput(String dataOutput) throws IOException;

    void deserializeDataInput(String dataInput) throws IOException;

}
