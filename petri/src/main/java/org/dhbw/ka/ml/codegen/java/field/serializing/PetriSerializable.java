package org.dhbw.ka.ml.codegen.java.field.serializing;

import java.io.Writer;

public interface PetriSerializable {

    void serializeDataOutput(String value, String dataOutput, Writer out);

    String deserializeDataInput(String dataInput, Writer out);

    void skip(String dataInput, Writer out);

}
