package com.salesforce.tests.fs.command.commandImplementation;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.fs.FileSystemTree;
import com.salesforce.tests.fs.fs.FsSimulation;
import com.salesforce.tests.fs.model.Node;

public class QuitCommand implements Command {
    public void execute() {
        FileSystemTree.save(FileSystemTree.getFilePath(), FileSystemTree.getFileSystemMap());
        FsSimulation.stopProgramm();
    }

    @Override
    public void setArgument(String pathToChange) {

    }

    @Override
    public void setCurrentNode(Node currentNode) {

    }
}
