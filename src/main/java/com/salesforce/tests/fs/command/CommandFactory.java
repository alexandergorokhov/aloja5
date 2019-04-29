package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.command.commandImplementation.*;

public class CommandFactory {

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
