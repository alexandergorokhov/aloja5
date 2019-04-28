package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public class ListContentCommand implements Command {
    private Node currentNode;
    private String path;

    public ListContentCommand(Node currentNode) {
        this.currentNode = currentNode;
    }

    public ListContentCommand() {
    }

    @Override
    public void execute() {
        if (this.path.contains("/")) {
            currentNode = currentNode.changeDirectoryConcatenated(this.path).get();
        }
            currentNode.listContent();


    }

    @Override
    public void setArgument(String pathToChange) {
        this.path = pathToChange;
    }

    @Override
    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }
}
