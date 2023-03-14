package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.DataInput;
import java.io.IOException;
import java.io.Writer;

@Data
public class BoolField implements PetriSerializable {

    private final ASTfield field;
    private final Writer out;

    @Override
    public void serializeDataOutput(String dataOutput) throws IOException {
        this.out.write(String.format(
                "%s.writeBoolean(this.%s);",
                dataOutput,
                this.field.getFieldIdent()
        ));
    }

    @Override
    public void deserializeDataInput(String dataInput) throws IOException {
        DataInput i;
        this.out.write(String.format(
                "this.%s = %s.readBoolean();",
                this.field.getFieldIdent(),
                dataInput
        ));
    }
}
