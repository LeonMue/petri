package org.dhbw.ka.ml.visitor.fieldnumber;

import org.dhbw.ka.ml.generated.*;

import java.util.*;

public class FieldNumberModifierVisitor implements PetriVisitor {
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
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTmessage node, Object data) {
        return node.childrenAccept(this, null);
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        // collect all field numbers
        var fields = new ArrayList<ASTfield>();
        node.childrenAccept(this, fields);

        fields.sort(Comparator.comparingInt(ASTfield::getFieldNumber));

        for (var i = 0; i < fields.size(); i++) {
            fields.get(i).setFieldNumber(i);
        }

        return null;
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        var fields = (List<ASTfield>) data;
        fields.add(node);
        return null;
    }
}
