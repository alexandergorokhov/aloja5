package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public class ListContentRecursive implements Command {
    private Node currentNode;

    public ListContentRecursive() {
    }

    public ListContentRecursive(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void execute() {
        currentNode.getCurrentAndChilds().forEach(System.out::println);
    }

    @Override
    public void setArgument(String pathToChange) {

    }

    @Override
    public void setCurrentNode(Node currentNode) {
        this.currentNode=currentNode;
    }
}
