package com.salesforce.tests.fs.model;

import java.util.Optional;
import java.util.stream.Stream;

public class File extends Node {
    @Override
    public void listContent() {
        System.out.println("Not a directory, can not list content");
    }

    @Override
    public void addChild(Node node) {
        System.out.println("Not a directory, can not have childs");
    }

    public File(String name) {
        this.name = name;
    }

    @Override
    public Stream<Node> getCurrentAndChilds() {
        return Stream.of(this);
    }

    @Override
    public Optional<Node> changeDirectoryConcatenated(String concatenatedPath) {
        return deny();
    }

    @Override
    public Optional<Node> changeDirectory(String name) {
        return deny();
    }

    private Optional<Node> deny() {
        System.out.println("Not a directory");
        return Optional.empty();
    }

    @Override
    public boolean isFile() {
        return true;
    }
}
