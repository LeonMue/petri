package org.dhbw.ka.ml.semantic.scopeduplications;

import lombok.Data;
import org.dhbw.ka.ml.generated.*;

import java.util.HashSet;
import java.util.Set;

public class ScopeDuplicationVisitor implements PetriVisitor {
    @Override
    public Object visit(SimpleNode node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTroot node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTComplexTypes node, Object data) {
        var complexTypesSymbolTable = new HashSet<String>();
        return node.childrenAccept(this, complexTypesSymbolTable);
    }

    @Override
    public Object visit(ASTmessage node, Object data) {
        // check for message identifier duplications
        var complexTypesSymbolTable = (Set<String>) data;
        var messageIdent = node.getIdent();
        if (complexTypesSymbolTable.contains(messageIdent)) {
            throw new IdentifierAlreadyDeclaredException(String.format(
                    "Message with ident '%s' already declared in the same scope. Consider to remove duplicated identifiers in the same scope.",
                    messageIdent
            ));
        }

        complexTypesSymbolTable.add(messageIdent);

        return node.childrenAccept(this, messageIdent);
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return node.childrenAccept(this, new FieldSymbolTable((String) data));
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        var fieldsSymbolTable = (FieldSymbolTable) data;

        var fieldIdentSymbolTable = fieldsSymbolTable.getIdentSymbolTable();
        var fieldIdent = node.getFieldIdent();
        if (fieldIdentSymbolTable.contains(fieldIdent)) {
            throw new IdentifierAlreadyDeclaredException(String.format(
                    "Field identifier '%s' in message '%s' already declared in the same scope. Consider to make identifier unique in the same scope.",
                    fieldIdent,
                    fieldsSymbolTable.getOfMessage()
            ));
        }
        fieldIdentSymbolTable.add(fieldIdent);

        var fieldNumberSymbolTable = fieldsSymbolTable.getNumberSymbolTable();
        var fieldNumber = node.getFieldNumber();
        if (fieldNumberSymbolTable.contains(fieldNumber)) {
            throw new FieldNumberAlreadyExistsException(String.format(
                    "Duplicated field number =%d in '%s/%s' found. Field numbers must be unique within one message scope.",
                    fieldNumber,
                    fieldsSymbolTable.getOfMessage(),
                    fieldIdent
            ));
        }
        fieldNumberSymbolTable.add(fieldNumber);

        return null;
    }

    @Data
    static class FieldSymbolTable {
        private final String ofMessage;
        private final Set<String> identSymbolTable = new HashSet<>();
        private final Set<Integer> numberSymbolTable = new HashSet<>();
    }
}
