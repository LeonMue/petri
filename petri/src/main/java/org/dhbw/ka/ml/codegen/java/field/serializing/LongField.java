package org.dhbw.ka.ml.codegen.java.field.serializing;

import lombok.Data;
import org.dhbw.ka.ml.generated.ASTfield;

import java.io.IOException;
import java.io.Writer;

@Data
public class LongField implements PetriSerializable {

    private final ASTfield field;
    private final Writer out;

    @Override
    public void serializeDataOutput(String dataOutput) throws IOException {
        this.out.write(String.format(
                "Varint.writeSignedVarLong(this.%s, %s);",
                this.field.getFieldIdent(),
                dataOutput
        ));
    }

    @Override
    public void deserializeDataInput(String dataInput) throws IOException {
        this.out.write(String.format(
                "this.%s = Varint.readSignedVarLong(%s);",
                this.field.getFieldIdent(),
                dataInput
        ));
    }
}
