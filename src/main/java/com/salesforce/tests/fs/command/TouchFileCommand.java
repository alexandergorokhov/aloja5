package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.File;
import com.salesforce.tests.fs.fs.Node;

public class TouchFileCommand implements Command {
    private Node currentNode;
    private String name;

    @Override
    public void execute() {
        if (CommandsUtils.isTheLengthWithinParameters(100, name, "File  name is too long")) {
            File file = new File(name);
            currentNode.addChild(file);
        }
    }

    public TouchFileCommand(Node currentNode, String name) {
        this.currentNode = currentNode;
        this.name = name;
    }

    public TouchFileCommand() {
    }

    @Override
    public void setArgument(String name) {
        this.name = name;
    }

    @Override
    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }
}
