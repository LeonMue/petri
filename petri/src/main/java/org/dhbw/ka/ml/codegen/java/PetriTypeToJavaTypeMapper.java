package org.dhbw.ka.ml.codegen.java;

import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class PetriTypeToJavaTypeMapper implements Function<String, String> {

    private static Map<String, String> typeMapping = Map.ofEntries(
            entry("bool", "boolean"),
            entry("int", "int"),
            entry("long", "long"),
            entry("float", "float"),
            entry("double", "double"),
            entry("string", "String")
    );

    @Override
    public String apply(String s) {
        return typeMapping.get(s);
    }

}
