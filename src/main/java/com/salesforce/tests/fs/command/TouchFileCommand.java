package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.File;
import com.salesforce.tests.fs.fs.Node;

public class TouchFileCommand implements Command {
    private Node currentNode;
    private String name;

    @Override
    public void execute() {
        File file = new File(name);
        currentNode.addChild(file);

    }

    public TouchFileCommand(Node currentNode, String name) {
        this.currentNode = currentNode;
        this.name = name;
    }
}
