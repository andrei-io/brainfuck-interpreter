package org.andreiio;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("BRAINFUCK Interpreter!");
        String filename = "src/main/java/org/andreiio/hello_world.b";
        String outFileName = "src/main/java/org/andreiio/output.txt";

        // If we set the delimiter to \\z the scanner reads the whole file into the string, newline and all
        try {
            String contents = new Scanner(new File(filename)).useDelimiter("\\z").next();
            var out = new PrintWriter(new File(outFileName));
            var machine = new Machine(contents, out, new Scanner(System.in));
            machine.runCode();
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}