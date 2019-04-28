package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public class CommandFactory {
   /* public Command getCommand(String command, Node currentNode, String additionalArguments) {
        switch (command) {
            case "quit":
                return new QuitCommand();
            case "pwd":
                return new PrintWorkingDirectoryCommand(currentNode);
            case "cd":
                return new ChangeDirectoryCommand(currentNode, additionalArguments);
            case "ls":
                return new ListContentCommand(currentNode);
            case "ls -r":
                return new ListContentRecursive(currentNode);
            case "mkdir":
                return new MakeDirectoryCommand(currentNode, additionalArguments);
            case "touch":
                return new TouchFileCommand(currentNode, additionalArguments);
            default:
                return null;
        }
    }*/

    public Command getCommand(String command) {
        switch (command) {
            case "quit":
                return new QuitCommand();
            case "pwd":
                return new PrintWorkingDirectoryCommand();
            case "cd":
                return new ChangeDirectoryCommand();
            case "ls":
                return new ListContentCommand();
            case "ls -r":
                return new ListContentRecursive();
            case "mkdir":
                return new MakeDirectoryCommand();
            case "touch":
                return new TouchFileCommand();
            default:
                return null;
        }
    }
}
