package org.dhbw.ka.ml.codegen.java.field.serializing;

public interface PetriSerializable {

    String serializeDataOutput(String value, String dataOutput);

    String deserializeDataInput(String dataInput);

    String skip(String dataInput);

}
