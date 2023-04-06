package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import org.dhbw.ka.ml.generated.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class JavaCodeGenerator implements PetriVisitor {

    private BufferedWriter out;
    private final Path outPath;
    private final String javaPackage;

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
        final var nodeIdent = node.getIdent().getIdent();
        final var filePath = Paths.get(this.outPath.toString(), nodeIdent + ".java");
        try (final var out = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            this.out = out;
            this.out.write(String.format("package %s;", this.javaPackage));
            this.out.write("import java.io.*;");
            this.out.write(String.format(
                    "public class %s {",
                    nodeIdent
            ));
            node.childrenAccept(this, null);
            node.childrenAccept(new MethodSerialize(this.out), null);
            node.childrenAccept(new MethodDeserialize(this.out, nodeIdent), null);
            new InternalDeserializeMethod(this.out).generate(node);
            out.write("}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        node.childrenAccept(new FieldDeclaration(this.out), null);
        node.childrenAccept(new Getter(this.out), null);
        node.childrenAccept(new Setter(this.out), null);
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
        return null;
    }
}
