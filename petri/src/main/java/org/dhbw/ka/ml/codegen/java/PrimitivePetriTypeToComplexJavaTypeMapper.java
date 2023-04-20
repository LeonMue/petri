package org.dhbw.ka.ml.codegen.java;

import java.util.Map;
import java.util.function.Function;

public class PrimitivePetriTypeToComplexJavaTypeMapper implements Function<String, String> {

    public static final Map<String, String> LOOKUP_MAP = Map.ofEntries(
            Map.entry("bool", "Boolean"),
            Map.entry("int32", "Integer"),
            Map.entry("int64", "Long"),
            Map.entry("float32", "Float"),
            Map.entry("float64", "Double"),
            Map.entry("string", "String")
    );

    @Override
    public String apply(String s) {
        return LOOKUP_MAP.get(s);
    }
}
