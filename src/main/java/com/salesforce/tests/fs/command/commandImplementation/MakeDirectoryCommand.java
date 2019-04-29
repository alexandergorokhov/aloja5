package com.salesforce.tests.fs.command.commandImplementation;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.command.CommandsUtils;
import com.salesforce.tests.fs.model.Directory;
import com.salesforce.tests.fs.model.Node;

public class MakeDirectoryCommand implements Command {
    private Node currentNode;
    private String name;

    @Override
    public void execute() {
        if (CommandsUtils.isTheLengthWithinParameters(100, name, "Directory name is too long")) {
            Directory directory = new Directory(this.name);
            currentNode.addChild(directory);
        }

    }

    @Override
    public void setArgument(String name) {
        this.name = name;
    }

    public MakeDirectoryCommand() {
    }

    @Override
    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }
}
