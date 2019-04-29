package com.salesforce.tests.fs.command.commandImplementation;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.model.Node;

public class NonValidCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Non valid command");
    }

    @Override
    public void setArgument(String pathToChange) {

    }

    @Override
    public void setCurrentNode(Node currentNode) {

    }
}
