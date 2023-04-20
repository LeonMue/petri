package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
            this.out.write("import java.util.List;");
            this.out.write("import java.util.ArrayList;");
            this.out.write(String.format(
                    "public class %s {",
                    nodeIdent
            ));
            node.childrenAccept(this, null);
            new SerializeMethodGenerator(this.out).generate(node);
            new DeserializeMethodGenerator(this.out).generate(node);
            new InternalDeserializeMethodGenerator(this.out).generate(node);
            out.write("}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ASTfields node, Object data) {
        node.childrenAccept(this, null);
        return null;
    }

    @SneakyThrows
    @Override
    public Object visit(ASTfield node, Object data) {
        final var generators = new FieldGenerators().getGenerators(node.getType(), node.getIdent().getIdent());
        generators.generateFieldDecl().generate(this.out);
        generators.generateHasField().generate(this.out);
        generators.generateGetMethod().generate(this.out);
        generators.generateSetMethod().generate(this.out);
        generators.generateHasMethod().generate(this.out);
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

    @Override
    public Object visit(ASTList node, Object data) {
        return null;
    }
}
