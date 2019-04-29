package com.salesforce.tests.fs.command.commandImplementation;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.model.Node;

public class ListContentRecursive implements Command {
    private Node currentNode;

    public ListContentRecursive() {
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
        this.currentNode = currentNode;
    }
}
