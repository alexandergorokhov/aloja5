package com.salesforce.tests.fs.fs;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.command.CommandFactory;
import com.salesforce.tests.fs.command.CommandsEnum;
import com.salesforce.tests.fs.command.NonValidCommand;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FsSimulation {
    private static Node currentNode;
    private static final CommandFactory commandFactory = new CommandFactory();
    private final String START_NODE = "/root";


    public static void setCurrentNode(Node currentNodeArg) {
        currentNode = currentNodeArg;
    }

    private static final Map<String, Command> commands;
    private static final Map<String, String> optionalArguments;
    private static boolean programRunning = true;

    static {
        final Map<String, Command> commandsLocal = new HashMap<>();
        commandsLocal.put("quit", commandFactory.getCommand(CommandsEnum.QUIT.toString()));
        commandsLocal.put("cd", commandFactory.getCommand(CommandsEnum.CHANGE_DIRECTORY.toString()));
        commandsLocal.put("pwd", commandFactory.getCommand(CommandsEnum.PRINT_WORKING_DIRECTORY.toString()));
        commandsLocal.put("ls", commandFactory.getCommand(CommandsEnum.LIST_CONTENT.toString()));
        commandsLocal.put("ls -r", commandFactory.getCommand(CommandsEnum.LIST_RECURSIVE.toString()));
        commandsLocal.put("mkdir", commandFactory.getCommand(CommandsEnum.MAKE_DIRECTORY.toString()));
        commandsLocal.put("touch", commandFactory.getCommand(CommandsEnum.CREATE_FILE.toString()));
        commands = Collections.unmodifiableMap(commandsLocal);

        final Map<String, String> optionalArgumentsLocal = new HashMap<>();
        optionalArgumentsLocal.put("ls -r", "ls -r");
        optionalArguments = Collections.unmodifiableMap(optionalArgumentsLocal);

    }

    public static void stopProgramm()
    {
        programRunning = false;
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
        Scanner scan = new Scanner(System.in);
        while (programRunning) {

            String aditionalArguments = "";
            CommandsEnum commandsEnum;
            String command = scan.nextLine();

            if (command.indexOf(" ") != -1 && !optionalArguments.containsKey(command)) {
                aditionalArguments = command.substring(command.indexOf(" ")).trim();
                commandsEnum = CommandsEnum.get(command.substring(0, command.indexOf(" ")));

            } else {
                commandsEnum = CommandsEnum.get(command);
            }
            Command commandToExecute = commands.getOrDefault(commandsEnum.toString(), new NonValidCommand());
            commandToExecute.setCurrentNode(currentNode);
            commandToExecute.setArgument(aditionalArguments);
            commandToExecute.execute();
        }
        scan.close();
    }





}
