package org.dhbw.ka.ml.petri.fieldnumber;

import org.dhbw.ka.ml.astmodification.AssignFieldNumbers;
import org.dhbw.ka.ml.generated.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;

public class FieldNumberModificationTest implements PetriVisitor {

    @Test
    void should_modifyFieldNumberOfAstInRightOrder_when_AssignFieldNumbersVisitorIsCalled() throws FileNotFoundException, ParseException {
        var petriFilePath = Paths.get("src", "test", "resources", "more_complex.petri");
        var parser = new Petri(new FileReader(petriFilePath.toFile()));
        var root = parser.root();

        root.jjtAccept(new AssignFieldNumbers(), null);
        root.jjtAccept(this, null);
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTroot node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTComplexTypes node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTstruct node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        final var fieldIdent = node.getIdent().getIdent();
        var fieldNumber = 0;
        switch (fieldIdent) {
            case "bool1" -> fieldNumber = 0;
            case "i" -> fieldNumber = 1;
            case "l" -> fieldNumber = 2;
            case "_1" -> fieldNumber = 3;
            case "dl" -> fieldNumber = 4;
            case "name" -> fieldNumber = 5;
            case "o" -> fieldNumber = 6;
            case "__11" -> fieldNumber = 0;
            case "l_ol__1234" -> fieldNumber = 1;
        }
        Assertions.assertEquals(node.getFieldNumber(), fieldNumber);
        return null;
    }

    @Override
    public Object visit(ASTPrimitiveType node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        return null;
    }
}
