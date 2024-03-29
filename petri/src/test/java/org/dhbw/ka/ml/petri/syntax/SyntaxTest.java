package org.dhbw.ka.ml.petri.syntax;

import org.dhbw.ka.ml.generated.ParseException;
import org.dhbw.ka.ml.generated.Petri;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SyntaxTest {

    private Path basePath = Paths.get("src", "test", "resources");

    @ParameterizedTest
    @ValueSource(strings = {
            "empty_struct.petri",
            "duplicated_field_ident.petri",
            "nothing.petri",
            "more_complex.petri",
            "semantic_valid_complex_object_types.petri",
            "lists_syntax.petri",
            "valid_lists_is_declared_semantic.petri",
            "invalid_lists_is_declared_semantic.petri",
            "invalid_nested_lists_is_declared_semantic.petri"
    })
    void validFiles_should_notThrowParseException(String filename) throws FileNotFoundException {
        var filePath = Paths.get(this.basePath.toString(), filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        Assertions.assertDoesNotThrow(parser::root);
    }

    @ParameterizedTest
    @ValueSource(strings = { "key_words_as_identifier.petri", "piano.petri", "invalid_lists_syntax.petri" })
    void invalidFiles_should_throwException(String filename) throws FileNotFoundException {
        var filePath = Paths.get(this.basePath.toString(), filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        Assertions.assertThrows(ParseException.class, parser::root);
    }

}
