package org.dhbw.ka.ml.visitor;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.codegen.java.LinePrinter;
import org.dhbw.ka.ml.codegen.java.Message;
import org.dhbw.ka.ml.codegen.java.PetriTypeToJavaTypeMapper;
import org.dhbw.ka.ml.codegen.java.field.serializing.PetriTypeToPetriSerializableMapper;
import org.dhbw.ka.ml.generated.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JavaGenVisitor implements PetriVisitor {

    private final Path generationPath;
    private final String packageName;

    @Override
    public Object visit(SimpleNode node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTroot node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTComplexTypes node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTmessage node, Object data) {
        final var fields = new ArrayList<ASTfield>();
        node.childrenAccept(this, fields);

        final var filePath = Paths.get(
                this.generationPath.toString(),
                node.getIdent() + ".java"
        );
        try (final var out = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            final var message = new Message(
                    this.packageName,
                    node.getIdent(),
                    new LinePrinter(out),
                    fields,
                    new PetriTypeToPetriSerializableMapper(),
                    new PetriTypeToJavaTypeMapper()
            );
            message.generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTfield node, Object data) {
        final var fields = (List<ASTfield>) data;
        fields.add(node);

        return null;
    }
}
