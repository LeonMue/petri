package org.dhbw.ka.ml.petri.semantic;

import org.dhbw.ka.ml.generated.ParseException;
import org.dhbw.ka.ml.generated.Petri;
import org.dhbw.ka.ml.semantic.IsDeclaredSemantic;
import org.dhbw.ka.ml.semantic.TypeNotDeclaredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IsDeclaredSemanticTest {

    static final Path basePath = Paths.get("src", "test", "resources");

    @ParameterizedTest
    @ValueSource(strings = {
            "semantic_valid_complex_object_types.petri",
            "valid_lists_is_declared_semantic.petri"
    })
    void validFiles_should_notThrowTypeNotDeclaredException(String filename) throws FileNotFoundException, ParseException {
        final var filePath = Paths.get(this.basePath.toString(), filename);
        final var parser = new Petri(new FileReader(filePath.toFile()));
        final var root = parser.root();

        Assertions.assertDoesNotThrow(() -> IsDeclaredSemantic.check(root));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "semantic_invalid_complex_object_types.petri",
            "duplicated_field_ident_complex_object_type.petri",
            "invalid_lists_is_declared_semantic.petri",
            "invalid_nested_lists_is_declared_semantic.petri"
    })
    void invalidFiles_should_throwTypeNotDeclaredException(String filename) throws FileNotFoundException, ParseException {
        final var filePath = Paths.get(this.basePath.toString(), filename);
        final var parser = new Petri(new FileReader(filePath.toFile()));
        final var root = parser.root();

        Assertions.assertThrows(TypeNotDeclaredException.class, () -> IsDeclaredSemantic.check(root));
    }

}
