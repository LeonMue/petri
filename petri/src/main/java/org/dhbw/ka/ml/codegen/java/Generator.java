package org.dhbw.ka.ml.codegen.java;

import java.io.IOException;
import java.io.Writer;

public interface Generator {

    void generate(Writer out) throws IOException;

}
