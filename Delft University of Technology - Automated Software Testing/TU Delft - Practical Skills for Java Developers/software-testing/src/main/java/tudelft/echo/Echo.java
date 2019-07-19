package tudelft.echo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ECHO(1)                   BSD General Commands Manual                  ECHO(1)
 * <p>
 * NAME
 * echo -- write arguments to the standard output
 * <p>
 * SYNOPSIS
 * echo [-n] [string ...]
 * <p>
 * DESCRIPTION
 * The echo utility writes any specified operands, separated by single blank
 * (' ') characters and followed by a newline (`\n') character, to the stan-
 * dard output.
 * <p>
 * The following option is available:
 * <p>
 * -n    Do not print the trailing newline character. This may also be
 *       achieved by appending `\c' to the end of the string.
 * <p>
 * EXIT STATUS
 * The echo utility exits 0 on success, and >0 if an error occurs.
 * <p>
 * BSD                             April 12, 2003                             BSD
 */
public class Echo {

    private String result = "";
    private SystemWrapper system = new SystemWrapper();

    @NotNull
    public Echo process(@NotNull List<String> args) {
        // make private copy that supports removing (first) argument.
        ArrayList<String> operands = new ArrayList(args);

        boolean trailingNewLine = true;
        result = "";

        // handle the -n flag, if present as first argument.
        if (!operands.isEmpty()) {
            if (operands.get(0).equals("-n")) {
                trailingNewLine = false;
                operands.remove(0);
            }
        }

        // handle a trailing \c character.
        if (!operands.isEmpty()) {
            int lastIndex = operands.size() - 1;
            String lastOperand = operands.get(lastIndex);
            if (lastOperand.endsWith("\\c")) {
                int len = lastOperand.length();
                assert len >= 2;
                lastOperand = lastOperand.substring(0, len - 2);
                operands.set(lastIndex, lastOperand);
                trailingNewLine = false;
            }
        }

        result = String.join(" ", operands);

        if (trailingNewLine) {
            result += "\n";
        }

        return this;
    }

    public void echo(String[] args) {
        process(Arrays.asList(args));
        system.print(output());
        system.exit(0);
    }

    @NotNull Echo withSystem(SystemWrapper sys) {
        system = sys;
        return this;
    }

    @NotNull
    public String output() {
        return result;
    }

    public static void main(String[] args) {
        new Echo().echo(args);
    }
}
