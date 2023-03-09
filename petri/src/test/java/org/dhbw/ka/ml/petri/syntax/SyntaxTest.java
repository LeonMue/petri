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

    private Path basePath = Paths.get("src", "test", "resources", "syntax");

    @ParameterizedTest
    @ValueSource(strings = { "empty_message.petri", "duplicated_field_number.petri", "nothing.petri" })
    void validFiles_should_notThrowParseException(String filename) throws FileNotFoundException {
        var filePath = Paths.get(this.basePath.toString(), "valid", filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        Assertions.assertDoesNotThrow(parser::root);
    }

    @ParameterizedTest
    @ValueSource(strings = { "key_words_as_identifier.petri" })
    void invalidFiles_should_throwException(String filename) throws FileNotFoundException {
        var filePath = Paths.get(this.basePath.toString(), "invalid", filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        Assertions.assertThrows(ParseException.class, parser::root);
    }

}
