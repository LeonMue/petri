package org.dhbw.ka.ml;

import org.apache.commons.cli.*;
import org.dhbw.ka.ml.astmodification.AssignFieldNumbers;
import org.dhbw.ka.ml.codegen.java.JavaCodeGenerator;
import org.dhbw.ka.ml.generated.Petri;
import org.dhbw.ka.ml.semantic.IsDeclaredSemantic;
import org.dhbw.ka.ml.semantic.scopeduplications.ScopedIdentDuplications;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        final var options = new Options();

        final var psdPathOption = Option.builder()
                .option("s")
                .longOpt("petri-file")
                .hasArg()
                .type(Path.class)
                .required()
                .desc("The path of the .psdl schema file you want to compile.")
                .build();

        final var javaOutOption = Option.builder()
                .longOpt("java-out")
                .hasArg()
                .type(Path.class)
                .required(false)
                .desc("This option indicates the compiler to generate java code! " +
                        "If you use this option, the option 'java-package' is also required. " +
                        "The value of this option is the output directory where petric will generate the code.")
                .build();
        final var javaPackageOption = Option.builder()
                .longOpt("java-package")
                .hasArg()
                .type(Package.class)
                .required(false)
                .desc("This option indicates the compiler to generate java code! " +
                        "If you use this option, the option 'java-out' is also required. " +
                        "The value of this option specifies the package of the generated java code.")
                .build();

        options.addOption(psdPathOption);
        options.addOption(javaOutOption);
        options.addOption(javaPackageOption);

        final var argParser = new DefaultParser();
        final var formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = argParser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("utility-help", options);
            System.exit(1);
        }

        final var psdPathValue = cmd.getOptionValue(psdPathOption);

        try {
            // parse tree
            final var petriParser = new Petri(new FileReader(psdPathValue));
            final var rootNode = petriParser.root();

            // semantic checking
            rootNode.jjtAccept(new ScopedIdentDuplications(), null);
            IsDeclaredSemantic.check(rootNode);

            // generate for java?
            final var javaOutputHasSet = cmd.hasOption(javaOutOption);
            final var javaPackageHasSet = cmd.hasOption(javaPackageOption);

            if (javaOutputHasSet ^ javaPackageHasSet) {
                System.out.println("It is required to set both 'java-out' and 'java-package' option if you " +
                        "use one of them...");
                System.exit(1);
            }

            if (javaOutputHasSet) {
                final var outputPath = cmd.getOptionValue(javaOutOption);
                final var javaPackage = cmd.getOptionValue(javaPackageOption);

                final var generationPath = Paths.get(outputPath);
                generationPath.toFile().mkdirs();

                rootNode.jjtAccept(new JavaCodeGenerator(
                        generationPath,
                        javaPackage
                ), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


        // modify field numbers
        // TODO: delete
        // rootNode.jjtAccept(new AssignFieldNumbers(), null);
    }

}
