package com.salesforce.tests.fs.command;

import java.util.HashMap;
import java.util.Map;

public enum CommandsEnum {
    QUIT("quit"),
    PRINT_WORKING_DIRECTORY("pwd"),
    LIST_CONTENT("ls"),
    LIST_RECURSIVE("ls -r"),
    MAKE_DIRECTORY("mkdir"),
    CHANGE_DIRECTORY("cd"),
    CREATE_FILE("touch"),
    COMMMAND_UNKNOWN("command unknow");

    private String command;

    CommandsEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    private static final Map<String, CommandsEnum> lookup = new HashMap<>();

    static {
        for (CommandsEnum command : CommandsEnum.values()) {
            lookup.put(command.getCommand(), command);
        }
    }

    public static CommandsEnum get(String command) {
        return lookup.getOrDefault(command, COMMMAND_UNKNOWN);
    }

    @Override
    public String toString() {
        return command;
    }
}
