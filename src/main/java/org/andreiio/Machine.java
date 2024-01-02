package org.andreiio;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Machine {
    private final int STACK_LENGTH = 30000;
    private final String code;
    private final int codeLength;
    private int instructionPointer;
    private int dataPointer;
    private final int[] memory;
    private final Writer outputChannel;
    private final Scanner inputChannel;


    public Machine(String code, Writer outputChannel, Scanner inputChannel) {
        this.code = code;
        this.codeLength = this.code.length();

        this.outputChannel = outputChannel;
        this.inputChannel = inputChannel;

        this.instructionPointer = 0;
        this.dataPointer = 0;
        this.memory = new int[this.STACK_LENGTH];
    }


    public void runCode() throws IOException {
        // Haven't run out of instructions
        while (instructionPointer < codeLength) {
            System.out.printf("Currently at instruction %d of %d\n", this.instructionPointer, this.codeLength);

            char instruction = this.code.charAt(instructionPointer);

            switch (instruction) {
                case '>':
                    this.handlePush();
                    break;
                case '<':
                    this.handlePop();
                    break;
                case '+':
                    this.handleIncrement();
                    break;
                case '-':
                    this.handleDecrement();
                    break;
                case '.':
                    this.handleOutput();
                    break;
                case ',':
                    this.handleInput();
                    break;
                case '[':
                    this.handleJumpIfZero();
                    break;
                case ']':
                    this.handleJumpNotZero();
                    break;

            }
            this.instructionPointer++;

        }
    }

    private void handlePush() {
        if (this.dataPointer == this.STACK_LENGTH - 1)
            throw new InvalidMemoryManipulationException(String.format("Invalid PUSH at instruction %d", this.instructionPointer));
        this.dataPointer++;
    }

    private void handlePop() {
        if (this.dataPointer == 0)
            throw new InvalidMemoryManipulationException(String.format("Invalid POP at instruction %d", this.instructionPointer));
        this.dataPointer--;
    }

    private void handleIncrement() {

        this.memory[this.dataPointer]++;
        if (this.memory[this.dataPointer] == 256)
            this.memory[this.dataPointer] = 0;
    }

    private void handleDecrement() {
        this.memory[this.dataPointer]--;
        if (this.memory[this.dataPointer] == -1)
            this.memory[this.dataPointer] = 255;

    }


    private void handleInput() {
        char raw = this.inputChannel.next().charAt(0);
        this.memory[this.dataPointer] = raw;
    }

    private void handleOutput() throws IOException {
        this.outputChannel.append((char) this.memory[this.dataPointer]);

    }


    private void handleJumpIfZero() {
        // Only search for the next bracket(loop close) if the value is not zero
        if (this.memory[this.dataPointer] != 0)
            return;

        // Brackets can be nested ,for example [+++++[----]++>] so we can't stop at the first ']'
        // Starting at 1 because we are sitting on a '['

        int openBrackets = 1;


        while (openBrackets != 0 && this.instructionPointer < this.codeLength) {
            this.instructionPointer++;
            char instruction = this.code.charAt(this.instructionPointer);
            if (instruction == '[')
                openBrackets++;
            if (instruction == ']')
                openBrackets--;
        }

    }

    private void handleJumpNotZero() {
        if (this.memory[this.dataPointer] == 0)
            return;

        // Brackets can be nested ,for example [+++++[----]++>] so we can't stop at the first ']'
        // Starting at 1 because we are sitting on a '['

        int openBrackets = 1;


        while (openBrackets != 0 && this.instructionPointer > 0) {
            this.instructionPointer--;
            char instruction = this.code.charAt(this.instructionPointer);
            if (instruction == ']')
                openBrackets++;
            if (instruction == '[')
                openBrackets--;
        }
    }


}
