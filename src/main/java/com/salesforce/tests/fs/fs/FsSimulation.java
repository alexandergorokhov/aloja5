package com.salesforce.tests.fs.fs;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.command.CommandFactory;
import com.salesforce.tests.fs.command.CommandsEnum;

import java.io.IOException;
import java.util.Scanner;

public class FsSimulation {
    private static Node currentNode;
    private CommandFactory commandFactory = new CommandFactory();
    private Command currentCommand;
    private final String START_NODE = "/root";

    public static void setCurrentNode(Node currentNodeArg) {
        currentNode = currentNodeArg;
    }

    public void simulation() {

        try {
            FileSystemTree.setFileSystemMap(FileSystemTree.load(FileSystemTree.getFilePath()));
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        currentNode = FileSystemTree.getFileSystemMap().get(START_NODE);
        boolean programRunning = true;
        Scanner scan = new Scanner(System.in);
        while (programRunning) {

            String aditionalArguments = "";
            CommandsEnum commandsEnum;
            String command = scan.nextLine();

            if (command.indexOf(" ") != -1) {
                aditionalArguments = command.substring(command.indexOf(" ")).trim();
                commandsEnum = CommandsEnum.get(command.substring(0, command.indexOf(" ")));
            } else {
                commandsEnum = CommandsEnum.get(command);
            }

            switch (commandsEnum.toString()) {
                case "quit":
                    programRunning = false;
                    executeTheCommand(commandsEnum, "");
                    break;
                case "cd":
                    String path = aditionalArguments;
                    executeTheCommand(commandsEnum, path);
                    break;
                case "pwd":
                    executeTheCommand(commandsEnum, "");
                    break;
                case "ls":
                    if (aditionalArguments.isEmpty()) {
                        executeTheCommand(commandsEnum, "");
                        break;
                    }
                    if (aditionalArguments.equals("-r")) {
                        executeTheCommand(CommandsEnum.LIST_RECURSIVE, "");
                        break;
                    }
                    if (aditionalArguments.contains("/")) {
                        executeTheCommand(CommandsEnum.CHANGE_DIRECTORY, aditionalArguments);
                        executeTheCommand(CommandsEnum.LIST_CONTENT, "");
                        break;
                    }
                    currentCommand.execute();
                    break;
                case "mkdir":
                    String nameDirectory = aditionalArguments;
                    if (isTheLengthWithinParameters(100, nameDirectory, "Directory name too long")) {
                        executeTheCommand(commandsEnum, nameDirectory);
                    }

                    break;
                case "touch":
                    String nameFile = aditionalArguments;
                    if (isTheLengthWithinParameters(100, nameFile, "File name name too long")) {
                        executeTheCommand(commandsEnum, nameFile);
                    }
                    break;
                default:
                    System.out.println("The command is not valid");
            }
        }
        scan.close();
    }

    private void executeTheCommand(CommandsEnum commandsEnum, String nameFile) {
        currentCommand = commandFactory.getCommand(commandsEnum.toString(), currentNode, nameFile);
        currentCommand.execute();
    }

    private boolean isTheLengthWithinParameters(int limit, String inspect, String message) {
        if (inspect.length() > limit) {
            System.out.println(message);
            return false;
        }
        return true;
    }
}
