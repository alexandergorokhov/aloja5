package com.salesforce.tests.fs.command.commandImplementation;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.command.CommandsUtils;
import com.salesforce.tests.fs.model.File;
import com.salesforce.tests.fs.model.Node;

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
