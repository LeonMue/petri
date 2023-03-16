package org.dhbw.ka.ml.codegen.java;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
public class LinePrinter {

    private final Writer out;

    public void write(String s, Object... args) throws IOException {
        this.out.write(String.format(s, args) + System.lineSeparator());
    }

}
