package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.fs.FileSystemTree;

public class QuitCommand implements Command {
    public void execute() {
        FileSystemTree.save(FileSystemTree.getFilePath(),FileSystemTree.getFileSystemMap());
    }
}
