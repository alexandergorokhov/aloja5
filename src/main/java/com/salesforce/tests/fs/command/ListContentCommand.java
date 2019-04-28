package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public class ListContentCommand implements Command {
    private Node currentNode;

    public ListContentCommand(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void execute() {
        currentNode.listContent();
    }
}
