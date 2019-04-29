package com.salesforce.tests.fs.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Node implements Serializable {
    protected List<Node> childs = new LinkedList<>();
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean isFile();

    public abstract void listContent();

    public abstract Stream<Node> getCurrentAndChilds();

    public abstract Optional<Node> changeDirectory(String name);

    public abstract void addChild(Node node);

    public abstract Optional<Node> changeDirectoryConcatenated(String concatenatedPath);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (childs != null ? !childs.equals(node.childs) : node.childs != null) return false;
        return name != null ? name.equals(node.name) : node.name == null;
    }

    @Override
    public int hashCode() {
        int result = childs != null ? childs.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

}