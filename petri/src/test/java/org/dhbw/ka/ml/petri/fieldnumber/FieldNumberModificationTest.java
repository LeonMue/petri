package org.dhbw.ka.ml.petri.fieldnumber;

import org.dhbw.ka.ml.astmodification.AssignFieldNumbers;
import org.dhbw.ka.ml.generated.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;

public class FieldNumberModificationTest implements PetriVisitor {

    private final FieldIdentVisitor fieldIdentVisitor = new FieldIdentVisitor();

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
    public Object visit(ASTParentIdentifier node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        this.fieldIdentVisitor.setFieldNumber(node.getFieldNumber());
        return node.childrenAccept(this.fieldIdentVisitor, null);
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
