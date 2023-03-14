package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.*;

@Data
public class IntField implements PetriSerializable {

    private final ASTfield field;
    private final Writer out;

    public void serializeDataOutput(String dataOutput) throws IOException {
        this.out.write(String.format(
                "Varint.writeSignedVarInt(this.%s, %s);",
                this.field.getFieldIdent(),
                dataOutput
        ));
    }

    public void deserializeDataInput(String dataInput) throws IOException {
        this.out.write(String.format(
                "this.%s = Varint.readSignedVarInt(%s);",
                this.field.getFieldIdent(),
                dataInput
        ));
    }

}
