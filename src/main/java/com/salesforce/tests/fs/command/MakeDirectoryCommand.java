package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Directory;
import com.salesforce.tests.fs.fs.Node;

public class MakeDirectoryCommand implements Command {
    private Node currentNode;
    private String name;


    public MakeDirectoryCommand(Node currentNode, String name) {
        this.currentNode = currentNode;
        this.name = name;
    }

    @Override
    public void execute() {
        if(CommandsUtils.isTheLengthWithinParameters(100,name,"Directory name is too long")){
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
