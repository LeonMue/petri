package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.DataOutput;
import java.io.IOException;
import java.io.Writer;

@Data
public class StringField implements PetriSerializable {

    private final ASTfield field;
    private final Writer out;

    @Override
    public void serializeDataOutput(String dataOutput) throws IOException {
        this.out.write("{");
        this.out.write(String.format(
                "byte[] bytesOfString = this.%s.getBytes(\"UTF-8\");",
                this.field.getFieldIdent()
        ));
        this.out.write(String.format(
                "Varint.writeUnsignedVarInt(bytesOfString.length, %s);",
                dataOutput
        ));
        this.out.write(String.format(
                "%s.write(bytesOfString);",
                dataOutput
        ));
        this.out.write("}");
    }

    @Override
    public void deserializeDataInput(String dataInput) throws IOException {
        this.out.write(String.format(
                "int length = Varint.readUnsignedVarInt(%s);",
                dataInput
        ));
        this.out.write("byte[] stringBytes = new byte[length];");
        this.out.write("for (int i = 0; i < length; i++) {");
        this.out.write(String.format(
                "stringBytes[i] = %s.readByte();",
                dataInput
        ));
        this.out.write("}");  // for
        this.out.write(String.format(
                "this.%s = new String(stringBytes, \"UTF-8\");",
                this.field.getFieldIdent()
        ));
    }
}
