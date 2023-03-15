package org.dhbw.ka.ml.codegen.java.field.serializing;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class PetriTypeToPetriSerializableMapper implements Function<String, PetriSerializable> {

    private static Map<String, PetriSerializable> mapping = Map.ofEntries(
            entry("bool", new BoolField()),
            entry("int", new IntField()),
            entry("long", new LongField()),
            entry("float", new FloatField()),
            entry("double", new DoubleField()),
            entry("string", new StringField())
    );

    @Override
    public PetriSerializable apply(String s) {
        return mapping.get(s);
    }
}
