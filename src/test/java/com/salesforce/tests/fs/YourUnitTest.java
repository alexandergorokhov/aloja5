package com.salesforce.tests.fs;

import com.salesforce.tests.fs.fs.Directory;
import com.salesforce.tests.fs.fs.File;
import com.salesforce.tests.fs.fs.FileSystemTree;
import com.salesforce.tests.fs.fs.Node;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Place holder for your unit tests
 */
public class YourUnitTest {
    private static Directory root;
    private static Node file1Level1;
    private static Node file2Level1;
    private static Directory directory1Level1;
    private static Directory directory2Level1;
    private static Node file1Level2;
    private static Directory directory1Level2;
    private static Node file2Level2;
    private static Node file3Level2;
    private static Node file1Level3;
    private static Node file2Level3;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeClass
    public static void setup() {

        root = (Directory) FileSystemTree.getFileSystemMap().get("/root");

        file1Level1 = new File("file1Level1");
        file2Level1 = new File("file2Level1");
        directory1Level1 = new Directory("directory1Level1");
        directory2Level1 = new Directory("directory2Level1");

        root.addChild(file1Level1);
        root.addChild(file2Level1);
        root.addChild(directory1Level1);
        root.addChild(directory2Level1);


        file1Level2 = new File("file1Level2");
        directory1Level2 = new Directory("directory1Level2");
        directory1Level1.addChild(file1Level2);
        directory1Level1.addChild(directory1Level2);

        file2Level2 = new File("file2Level2");
        file3Level2 = new File("file3Level2");
        directory2Level1.addChild(file2Level2);
        directory2Level1.addChild(file3Level2);

        file1Level3 = new File("file1Level3");
        file2Level3 = new File("file2Level3");

        directory1Level2.addChild(file1Level3);
        directory1Level2.addChild(file2Level3);

    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    //----PWD
    @Test
    public void PWDCommandFileTest() {
        String result = file2Level2.getName();
        Assert.assertEquals("/root/directory2Level1/file2Level2", result);
    }

    @Test
    public void PWDCommandDirectoryTest() {
        String result = directory1Level2.getName();
        Assert.assertEquals("/root/directory1Level1/directory1Level2", result);
    }

    @Test
    public void PWDCommandRootTest() {
        String result = root.getName();
        Assert.assertEquals("/root", result);
    }

    // ---LS
    @Test
    public void LsDirectoryCommandTest() {
        List<Node> results = directory1Level1.getChilds();
        List<Node> expected = new LinkedList<>();
        expected.add(file1Level2);
        expected.add(directory1Level2);
        Assert.assertEquals(expected, results);
    }


    @Test
    public void LsRootCommandTest() {
        List<Node> results = root.getChilds();
        List<Node> expected = new LinkedList<>();
        expected.add(file1Level1);
        expected.add(file2Level1);
        expected.add(directory1Level1);
        expected.add(directory2Level1);
        Assert.assertEquals(expected, results);
    }

    // LS -r
    @Test
    public void recursiveLsRootCommandTest() {
        // First files than directories, depth First
        List<Node> results = root.getCurrentAndChilds().collect(Collectors.toList());
        List<Node> expected = new LinkedList<>();
        expected.add(root);
        expected.add(file1Level1);
        expected.add(file2Level1);
        expected.add(directory1Level1);
        expected.add(file1Level2);
        expected.add(directory1Level2);
        expected.add(directory2Level1);
        expected.add(file2Level2);
        expected.add(file3Level2);

        Assert.assertTrue(results.containsAll(expected));
    }

    @Test
    public void recursiveLsDirectoryCommandTest() {
        // First files than directories, depth First
        List<Node> results = directory2Level1.getCurrentAndChilds().collect(Collectors.toList());
        List<Node> expected = new LinkedList<>();
        expected.add(directory2Level1);
        expected.add(file2Level2);
        expected.add(file3Level2);
        Assert.assertEquals(expected, results);
    }

    @Test
    public void recursiveLsFileCommandTest() {
        // First files than directories, depth First
        List<Node> results = file2Level2.getCurrentAndChilds().collect(Collectors.toList());
        List<Node> expected = new LinkedList<>();
        expected.add(file2Level2);
        Assert.assertEquals(results, expected);

    }

    @Test
    public void listFinalNodeGivenPathCommandTest() {
        Node node = root.changeDirectoryConcatenated("directory1Level1/directory1Level2").get();
        List<Node> results = node.getCurrentAndChilds().collect(Collectors.toList());
        List<Node> expected = new LinkedList<>();
        expected.add(directory1Level2);
        expected.add(file1Level3);
        expected.add(file2Level3);
        Assert.assertEquals(results, expected);

    }

    // MKDIR

    @Test
    public void mkdirExistingdiretoryCommandTest() {
        Directory directory = new Directory("directory2Level1");
        root.addChild(directory);
        Assert.assertFalse(FileSystemTree.getFileSystemMap().containsKey(directory));

    }

    //CD
    @Test
    public void changeInexistingDirectoryDownByNameCommandTest() {
        Optional<Node> node = directory1Level1.changeDirectoryLevelDownByName("notExistingDirectory");
        Assert.assertEquals("Directory not found" + System.getProperty("line.separator"), outContent.toString());
        Assert.assertTrue(!node.isPresent());
    }

    @Test
    public void changeExistingDirectoryDownByNameCommandTest() {
        Optional<Node> node = directory1Level1.changeDirectoryLevelDownByName("directory1Level2");
        Assert.assertEquals(node.get(), directory1Level2);
    }

    @Test
    public void changeExistingDirectoryUpCommandTest() {
        Optional<Node> node = directory1Level2.changeDirectoryLevelUp();
        Assert.assertEquals(node.get(), directory1Level1);
    }

    @Test
    public void changeExistingDirectoryUpToRootCommandTest() {
        Optional<Node> node = directory1Level1.changeDirectoryLevelUp();
        Assert.assertEquals(node.get(), root);
    }

    @Test
    public void changeExistingDirectoryUpToRootBeingOnRootCommandTest() {
        Optional<Node> node = root.changeDirectoryLevelUp();
        Assert.assertEquals(node.get(), root);
    }

    @Test
    public void changeExistingDirectoryDownByNameRecursivelyCommandTest() {
        Optional<Node> node = root.changeDirectoryConcatenated("directory1Level1/directory1Level2/..");
        Assert.assertEquals(node.get(),directory1Level1);
    }

    @Test
    public void changeExistingDirectoryUpByNameRecursivelyCommandTest() {
        Optional<Node> node = directory1Level1.changeDirectoryConcatenated("../directory2Level1");
        Assert.assertEquals(node.get(),directory2Level1);
    }

    @Test
    public void changeExistingDirectoryUpByNameRecursivelyToRootCommandTest() {
        Optional<Node> node = directory1Level2.changeDirectoryConcatenated("../..");
        Assert.assertEquals(node.get(),root);
    }

    @Test
    public void changeInxistingDirectoryUpByNameRecursivelyToRootCommandTest() {
        Optional<Node> node = directory1Level2.changeDirectoryConcatenated("../test/root");
        Assert.assertEquals("Invalid path" + System.getProperty("line.separator"), outContent.toString());
        Assert.assertEquals(node,Optional.empty());
    }



    //Touch
    @Test
    public void createNewFileCommandTest() {
        File file = new File("newFile");
        directory1Level1.addChild(file);
        Node result = FileSystemTree.getFileSystemMap().remove(file.getName());
        Assert.assertEquals(result, file);
    }

    @Test
    public void saveLoadTest() {
        HashMap map = new HashMap();
        map.put("uno", 1);
        map.put("ein",1);
        FileSystemTree.save("testMap",map);
        map.put("twei",2);
        HashMap result = null;
        try {
            result = FileSystemTree.load("testMap");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(result.equals(map));
        map.remove("twei");
        Assert.assertTrue(result.equals(map));
    }


}
