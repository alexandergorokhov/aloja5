package com.salesforce.tests.fs.fs;

import java.io.*;
import java.util.HashMap;

public class FileSystemTree {

    private static HashMap<String, Node> fileSystemMap = new HashMap();
    private static final String FILE_PATH = "file_system_tree";

    public static String getFilePath() {
        return FILE_PATH;
    }

    static {
        fileSystemMap.put("/root", new Directory("/root"));
    }

    public static void addCacheRoot(String path, Node node) {
        fileSystemMap.put(path, node);
    }

    public static boolean isPathPresentInCahce(String path) {
        return fileSystemMap.containsKey(path);
    }

    public static HashMap<String, Node> getFileSystemMap() {
        return fileSystemMap;
    }

    public static void setFileSystemMap(HashMap<String, Node> fileSystemMap) {
        FileSystemTree.fileSystemMap = fileSystemMap;
    }

    public static void save(String path, HashMap map) {
        try (FileOutputStream fos = new FileOutputStream(path);) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap load(String path) throws IOException, ClassNotFoundException {
        HashMap resultMap = new HashMap();
        try (FileInputStream fis = new FileInputStream(path);) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            resultMap = (HashMap) ois.readObject();
        } catch (IOException e) {
            throw new IOException("Archive is not present");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class is not found");
        }
        return resultMap;
    }

    //Can be used to populate the initial File System Tree
    public static void populateMapForSimulation() {
        Node root = FileSystemTree.getFileSystemMap().get("root");
        Node file1Level1 = new File("file1Level1");
        Node file2Level1 = new File("file2Level1");
        Node directory1Level1 = new Directory("directory1Level1");
        Node directory2Level1 = new Directory("directory2Level1");

        root.addChild(file1Level1);
        root.addChild(file2Level1);
        root.addChild(directory1Level1);
        root.addChild(directory2Level1);


        Node file1Level2 = new File("file1Level2");
        Node directory1Level2 = new Directory("directory1Level2");
        directory1Level1.addChild(file1Level2);
        directory1Level1.addChild(directory1Level2);

        Node file2Level2 = new File("file2Level2");
        Node file3Level2 = new File("file3Level2");
        directory2Level1.addChild(file2Level2);
        directory2Level1.addChild(file3Level2);

        Node file1Level3 = new File("file1Level3");
        Node file2Level3 = new File("file2Level3");

        directory1Level2.addChild(file1Level3);
        directory1Level2.addChild(file2Level3);
    }
}
