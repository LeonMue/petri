package org.dhbw.ka.ml.codegen.java.field.serializing;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class PetriTypeToPetriSerializableMapper implements Function<String, PetriSerializable> {

    private static Map<String, PetriSerializable> mapping = Map.ofEntries(
            entry("bool", new BoolField()),
            entry("int32", new IntField()),
            entry("int64", new LongField()),
            entry("float32", new FloatField()),
            entry("float64", new DoubleField()),
            entry("string", new StringField())
    );

    @Override
    public PetriSerializable apply(String s) {
        return mapping.get(s);
    }
}
