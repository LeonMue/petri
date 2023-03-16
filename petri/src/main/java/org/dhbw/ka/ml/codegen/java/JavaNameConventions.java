package org.dhbw.ka.ml.codegen.java;

public class JavaNameConventions {

    public static String firstLetterUpperCase(String name) {
        final var firstLetterUpperCase = Character.toUpperCase(name.charAt(0));
        final var tail = name.substring(1);

        return String.format("%c%s", firstLetterUpperCase, tail);
    }

}
