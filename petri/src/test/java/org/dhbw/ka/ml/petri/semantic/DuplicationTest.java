package org.dhbw.ka.ml.petri.semantic;

import org.dhbw.ka.ml.semantic.scopeduplications.IdentifierAlreadyDeclaredException;
import org.dhbw.ka.ml.generated.ParseException;
import org.dhbw.ka.ml.generated.Petri;
import org.dhbw.ka.ml.semantic.scopeduplications.ScopedIdentDuplications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DuplicationTest {

    private Path basePath = Paths.get("src", "test", "resources");

    @ParameterizedTest
    @ValueSource(strings = { "more_complex.petri" })
    void validFiles_should_notThrowDuplicatedNamingException(String filename) throws FileNotFoundException, ParseException {
        var filePath = Paths.get(this.basePath.toString(), filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        var root = parser.root();

        Assertions.assertDoesNotThrow(() -> root.jjtAccept(new ScopedIdentDuplications(), null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "duplicated_field_ident.petri",
            "duplicated_struct_decls.petri",
            "duplicated_field_ident_complex_object_type.petri"
    })
    void invalidFiles_should_throwIdentifierAlreadyDeclaredException(String filename) throws FileNotFoundException, ParseException {
        var filePath = Paths.get(this.basePath.toString(), filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        var root = parser.root();

        Assertions.assertThrows(
                IdentifierAlreadyDeclaredException.class,
                () -> root.jjtAccept(new ScopedIdentDuplications(), null)
        );
    }

}
