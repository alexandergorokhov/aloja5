package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public class PrintWorkingDirectoryCommand implements Command {
    private Node currentNode;

    public PrintWorkingDirectoryCommand(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void execute() {
        System.out.println(currentNode.getName());

    }
}
