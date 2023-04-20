package org.dhbw.ka.ml.codegen.java;

import org.dhbw.ka.ml.generated.*;

public class FieldGenerators {

    private static final PrimitivePetriTypeToPrimitiveJavaTypeMapper PRIMITIVE_PETRI_TYPE_TO_PRIMITIVE_JAVA_TYPE_MAPPER = new PrimitivePetriTypeToPrimitiveJavaTypeMapper();

    public Result getGenerators(SimpleNode typeIdent, String fieldIdent) {
        final var internalVisitor = new Visitor(fieldIdent, GetFieldTypeIdent.getFieldTypeIdent(typeIdent, PRIMITIVE_PETRI_TYPE_TO_PRIMITIVE_JAVA_TYPE_MAPPER));

        typeIdent.jjtAccept(internalVisitor, null);
        return new Result(
                internalVisitor.generateFieldDecl,
                internalVisitor.generateHasField,
                internalVisitor.generateGetMethod,
                internalVisitor.generateHasMethod,
                internalVisitor.generateSetMethod
                );
    }

    record Result(
            Generator generateFieldDecl,
            Generator generateHasField,
            Generator generateGetMethod,
            Generator generateHasMethod,
            Generator generateSetMethod
    ) {
    }

    private class Visitor implements PetriVisitor {
        private final String fieldIdent;

        private final String typeIdent;

        private Generator generateFieldDecl;
        private Generator generateHasField;

        private Generator generateGetMethod;

        private Generator generateHasMethod = out -> {};

        private Generator generateSetMethod = out -> {};

        public Visitor(String fieldIdent, String typeIdent) {
            this.fieldIdent = fieldIdent;
            this.typeIdent = typeIdent;

            this.generateFieldDecl = out -> out.write(String.format(
                    "private %s %s;",
                    this.typeIdent,
                    this.fieldIdent
            ));

            this.generateHasField = out -> out.write(String.format(
                    "private boolean has%s;",
                    JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
            ));

            this.generateGetMethod = out -> {
                out.write(String.format(
                        "public %s get%s() {",
                        this.typeIdent,
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                ));
                out.write(String.format(
                        "return this.%s;",
                        this.fieldIdent
                ));
                out.write("}");
            };
        }

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
        public Object visit(ASTfields node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTfield node, Object data) {
            return null;
        }

        @Override
        public Object visit(ASTPrimitiveType node, Object data) {
            this.generateHasField = node.getType().equals("string") ? out -> {} : this.generateHasField;
            this.generateSetMethod = out -> {
                out.write(String.format(
                        "public void set%s(%s value) {",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent),
                        this.typeIdent
                ));
                if (!node.getType().equals("string")) {
                    out.write(String.format(
                            "this.has%s = true;",
                            JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                    ));
                }
                out.write(String.format(
                        "this.%s = value;",
                        this.fieldIdent
                ));
                out.write("}");
            };
            this.generateHasMethod = out -> {
                out.write(String.format(
                        "public boolean has%s() {",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                ));
                if (node.getType().equals("string")) {
                    out.write(String.format(
                            "return this.%s != null;",
                            this.fieldIdent
                    ));
                } else {
                    out.write(String.format(
                            "return this.has%s;",
                            JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                    ));
                }
                out.write("}");
            };
            return null;
        }

        @Override
        public Object visit(ASTIdentifier node, Object data) {
            this.generateHasField = out -> {};
            this.generateHasMethod = out -> {
                out.write(String.format(
                        "public boolean has%s() {",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                ));
                out.write(String.format(
                        "return this.%s != null;",
                        this.fieldIdent
                ));
                out.write("}");
            };
            this.generateSetMethod = out -> {
                out.write(String.format(
                        "public void set%s(%s value) {",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent),
                        this.typeIdent
                ));
                out.write(String.format(
                        "this.%s = value;",
                        fieldIdent
                ));
                out.write("}");
            };
            return null;
        }

        @Override
        public Object visit(ASTList node, Object data) {
            this.generateFieldDecl = out -> out.write(String.format(
                    "private %s %s;",
                    this.typeIdent,
                    this.fieldIdent
            ));
            this.generateHasField = out -> {};
            this.generateSetMethod = out -> {
                out.write(String.format(
                        "public void set%s(%s value) {",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent),
                        this.typeIdent
                ));
                out.write(String.format(
                        "this.%s = value;",
                        this.fieldIdent
                ));
                out.write("}");  // set
            };
            this.generateHasMethod = out -> {
                out.write(String.format(
                        "public boolean has%s() {",
                        JavaNameConventions.firstLetterUpperCase(this.fieldIdent)
                ));
                out.write(String.format(
                        "return this.%s != null;",
                        this.fieldIdent
                ));
                out.write("}");  // has
            };

            return null;
        }
    }

}
