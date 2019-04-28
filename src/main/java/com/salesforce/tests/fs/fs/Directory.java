package com.salesforce.tests.fs.fs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Directory extends Node {
    private final String PARRENT_PATH = "..";
    private final String ROOT_NODE = "/root";

    public Directory(String name) {
        this.name = name;

    }


    @Override
    public void listContent() {
        if (!getChilds().isEmpty()) {
            System.out.println(getChilds());
        }
    }

    public List<Node> getChilds() {
        return childs;
    }

    public boolean isFile() {
        return false;

    }

    public Stream<Node> getCurrentAndChilds() {

        return Stream.concat(
                Stream.of(this),
                childs.stream().flatMap(Node::getCurrentAndChilds));
    }

    public void addChild(Node node) {
        node.setName(this.getName() + "/" + node.getName());
        if (FileSystemTree.isPathPresentInCahce(node.getName())) {
            System.out.println("Directory already exists");
        } else {
            FileSystemTree.addCacheRoot(node.getName(), node);
            childs.add(node);
        }
    }

    public Optional<Node> changeDirectory(String name) {
        Optional<Node> current = null;

        String[] paths = name.split("/");
        for (int i = 0; i < paths.length; i++) {

            if (paths[i].equals(PARRENT_PATH)) {
                current = changeDirectoryLevelUp();
            } else {
                current = changeDirectoryLevelDownByName(paths[i]);
            }
        }
        if (current.isPresent() && current.get().isFile()) {

            System.out.println("Not a directory");
            return Optional.empty();

        }
        return current;

    }


    public Optional<Node> changeDirectoryLevelDownByName(String name) {
        String absolutePath = this.getName().concat("/").concat(name);
        Optional<Node> node = childs.stream().filter(node1 -> node1.getName().equals(absolutePath)).findAny();
        if (!node.isPresent()) {
            System.out.println("Directory not found");
        }
        return node;
    }

    public Optional<Node> changeDirectoryLevelUp() {
        if (this.getName().equals(ROOT_NODE)) {
            return Optional.of(this);
        }
        String absolutePath = getParentPath(this.getName());
        Node node = FileSystemTree.getFileSystemMap().get(absolutePath);
        return Optional.of(node);
    }

    private String getParentPath(String pathChild) {
        int index = pathChild.lastIndexOf("/");
        return pathChild.substring(0, index);
    }

    public Optional<Node> changeDirectoryConcatenated(String concatenatedPath) {
        String[] paths = concatenatedPath.split("/");
        String nodePath = this.getName();
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].equals(PARRENT_PATH)) {
                nodePath = getParentPath(nodePath);
            } else {
                nodePath = nodePath.concat("/").concat(paths[i]);
            }

        }
        Optional<Node> node = Optional.ofNullable(FileSystemTree.getFileSystemMap().get(nodePath));
        if (nodePath.equals("")) {
            return Optional.empty();
        }
        if (!node.isPresent()) {

            if (paths.length > 1) {
                System.out.println("Invalid path");
            } else {
                System.out.println("Directory not found");
            }
        }
        if (node.isPresent() && node.get().isFile()) {

            System.out.println("Not a directory");
            return Optional.empty();

        }
        return node;
    }

}
