package org.dhbw.ka.ml.petri.fieldnumber;

import lombok.Data;
import org.dhbw.ka.ml.generated.*;
import org.junit.jupiter.api.Assertions;

@Data
public class FieldIdentVisitor implements PetriVisitor {

    private int fieldNumber;

    @Override
    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTroot node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTComplexTypes node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTstruct node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTParentIdentifier node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTPrimitiveType node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        var fieldNumber = 0;
        var i = node.getIdent();
        switch (node.getIdent()) {
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
        Assertions.assertEquals(this.fieldNumber, fieldNumber);
        return null;
    }
}
