package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public class ListContentRecursive implements Command {
    private Node currentNode;

    public ListContentRecursive(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void execute() {
        currentNode.getCurrentAndChilds().forEach(System.out::println);
    }
}
