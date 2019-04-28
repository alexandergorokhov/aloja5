package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.Node;

public interface Command {
    public void execute();
    public void setArgument(String pathToChange);
    public void setCurrentNode(Node currentNode);
}
