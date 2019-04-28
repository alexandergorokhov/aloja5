package com.salesforce.tests.fs.command;

public class CommandsUtils {
    public static boolean isTheLengthWithinParameters(int limit, String inspect, String message) {
        if (inspect.length() > limit) {
            System.out.println(message);
            return false;
        }
        return true;
    }
}
