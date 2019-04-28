package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.FsSimulation;
import com.salesforce.tests.fs.fs.Node;

import java.util.Optional;

public class ChangeDirectoryCommand implements Command {
    private Node currentNode;
    private String pathToChange;

    public ChangeDirectoryCommand(Node currentNode, String pathToChange) {
        this.currentNode = currentNode;
        this.pathToChange = pathToChange;
    }

    public ChangeDirectoryCommand() {
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public void setArgument(String pathToChange) {
        this.pathToChange = pathToChange;
    }

    @Override
    public void execute() {
        Optional<Node> node = currentNode.changeDirectoryConcatenated(pathToChange);
        FsSimulation.setCurrentNode(node.orElse(currentNode));


    }
}
