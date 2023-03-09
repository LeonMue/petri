package org.dhbw.ka.ml.petri.semantic;

import org.dhbw.ka.ml.visitor.scopeduplications.FieldNumberAlreadyExistsException;
import org.dhbw.ka.ml.visitor.scopeduplications.ScopeDuplicationVisitor;
import org.dhbw.ka.ml.visitor.scopeduplications.IdentifierAlreadyDeclaredException;
import org.dhbw.ka.ml.generated.ParseException;
import org.dhbw.ka.ml.generated.Petri;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DuplicationTest {

    private Path basePath = Paths.get("src", "test", "resources", "semantic");

    @ParameterizedTest
    @ValueSource(strings = { "valid.petri" })
    void validFiles_should_notThrowDuplicatedNamingException(String filename) throws FileNotFoundException, ParseException {
        var filePath = Paths.get(this.basePath.toString(), "valid", filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        var root = parser.root();

        Assertions.assertDoesNotThrow(() -> { root.jjtAccept(new ScopeDuplicationVisitor(), null); });
    }

    @ParameterizedTest
    @ValueSource(strings = { "duplicated_field_ident.petri" })
    void invalidFiles_should_throwIdentifierAlreadyDeclaredException(String filename) throws FileNotFoundException, ParseException {
        var filePath = Paths.get(this.basePath.toString(), "invalid", filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        var root = parser.root();

        Assertions.assertThrows(
                IdentifierAlreadyDeclaredException.class,
                () -> root.jjtAccept(new ScopeDuplicationVisitor(), null)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "duplicated_field_number.petri" })
    void invalidFiles_should_throwFieldNumberAlreadyExistsException(String filename) throws FileNotFoundException, ParseException {
        var filePath = Paths.get(this.basePath.toString(), "invalid", filename);
        var parser = new Petri(new FileReader(filePath.toFile()));
        var root = parser.root();

        Assertions.assertThrows(
                FieldNumberAlreadyExistsException.class,
                () -> { root.jjtAccept(new ScopeDuplicationVisitor(), null); }
        );
    }

}
