package com.salesforce.tests.fs;

import com.salesforce.tests.fs.command.Command;
import com.salesforce.tests.fs.command.commandImplementation.*;
import com.salesforce.tests.fs.fs.*;
import com.salesforce.tests.fs.model.Directory;
import com.salesforce.tests.fs.model.File;
import com.salesforce.tests.fs.model.Node;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
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
    private ByteArrayInputStream inContent;

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        inContent = new ByteArrayInputStream(data.getBytes());
        System.setIn(inContent);

    }

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
        HashMap map = FileSystemTree.getFileSystemMap();

    }


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


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
    public void pwdCommandRootTest() {
        String result = root.getName();
        Assert.assertEquals("/root", result);
    }

    // ---LS
    @Test
    public void lsDirectoryCommandTest() {
        List<Node> results = directory1Level1.getChilds();
        List<Node> expected = new LinkedList<>();
        expected.add(file1Level2);
        expected.add(directory1Level2);
        Assert.assertTrue(results.containsAll(expected));
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
    public void mkdirExistingDiretoryCommandTest() {
        Directory directory = new Directory("directory2Level1");
        root.addChild(directory);
        Assert.assertEquals("Directory already exists" + System.getProperty("line.separator"), outContent.toString());
    }

    @Test
    public void mkdirTooLongNameTest() {
        Command command = new MakeDirectoryCommand();
        command.setCurrentNode(root);
        command.setArgument(StringUtils.repeat("a", 101));
        command.execute();
        Assert.assertEquals("Directory name is too long" + System.getProperty("line.separator"), outContent.toString());
    }

    @Test
    public void mkdirNonExistingDiretoryCommandTest() {
        Directory directory = new Directory("directoryNewLevel1");
        root.addChild(directory);
        Assert.assertTrue(FileSystemTree.getFileSystemMap().containsKey(directory.getName()));
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
        Assert.assertEquals(node.get(), directory1Level1);
    }

    @Test
    public void changeExistingDirectoryUpByNameRecursivelyCommandTest() {
        Optional<Node> node = directory1Level1.changeDirectoryConcatenated("../directory2Level1");
        Assert.assertEquals(node.get(), directory2Level1);
    }

    @Test
    public void changeExistingDirectoryUpByNameRecursivelyToRootCommandTest() {
        Optional<Node> node = directory1Level2.changeDirectoryConcatenated("../..");
        Assert.assertEquals(node.get(), root);
    }

    @Test
    public void changeInxistingDirectoryUpByNameRecursivelyToRootCommandTest() {
        Optional<Node> node = directory1Level2.changeDirectoryConcatenated("../test/root");
        Assert.assertEquals("Invalid path" + System.getProperty("line.separator"), outContent.toString());
        Assert.assertEquals(node, Optional.empty());
    }


    //Touch
    @Test
    public void createNewFileCommandTest() {
        File file = new File("newFile");
        directory1Level1.addChild(file);
        Node result = FileSystemTree.getFileSystemMap().get(file.getName());
        Assert.assertEquals(result, file);
    }

    @Test
    public void touchTooLongNameTest() {
        Command command = new TouchFileCommand();
        command.setCurrentNode(root);
        command.setArgument(StringUtils.repeat("a", 101));
        command.execute();
        Assert.assertEquals("File  name is too long" + System.getProperty("line.separator"), outContent.toString());
    }

    @Test
    public void saveLoadTest() {
        HashMap map = new HashMap();
        map.put("uno", 1);
        map.put("ein", 1);
        FileSystemTree.save("testMap", map);
        map.put("twei", 2);
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

    // Command

    @Test
    public void listCommandTest() {
        Command command = new ListContentCommand();
        command.setCurrentNode(root);
        command.setArgument("root");
        command.execute();
        Assert.assertEquals("[/root/file1Level1, /root/file2Level1, /root/directory1Level1, /root/directory2Level1, /root/directoryNewLevel1]" + System.getProperty("line.separator"), outContent.toString());
    }

    @Test
    public void listWithPathCommandTest() {
        Command command = new ListContentCommand();
        command.setCurrentNode(root);
        command.setArgument("directory1Level1&directory1Level2");
        command.execute();
        Assert.assertEquals("[/root/file1Level1, /root/file2Level1, /root/directory1Level1, /root/directory2Level1]" + System.getProperty("line.separator"), outContent.toString());
    }

    @Test
    public void listContentRecursivelyCommandTest() {
        Command command = new ListContentRecursive();
        command.setCurrentNode(directory1Level1);
        command.execute();
        Assert.assertEquals("/root/directory1Level1" + System.getProperty("line.separator")
                        + "/root/directory1Level1/file1Level2" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2/file1Level3" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2/file2Level3" + System.getProperty("line.separator")
                        + "/root/directory1Level1/newFile" + System.getProperty("line.separator")

                , outContent.toString());
    }


    @Test
    public void changeDirectoryCommandTest() {
        Command command = new ChangeDirectoryCommand();
        command.setCurrentNode(root);
        command.setArgument("directory1Level1");
        command.execute();
        Node current = FsSimulation.getCurrentNode();
        Assert.assertEquals("/root/directory1Level1", current.getName());
    }

    @Test
    public void nonValidCommandTest() {
        Command command = new NonValidCommand();
        command.execute();
        Assert.assertEquals("Non valid command" + System.getProperty("line.separator"), outContent.toString());
    }

    @Ignore
    @Test
    public void fwSimulation() throws IOException {

        java.io.File createdFile = folder.newFile("testFile.txt");
        FileSystemTree.setFilePath(createdFile.getPath());
        FsSimulation fsSimulation = new FsSimulation();

        provideInput("pwd" + System.getProperty("line.separator")
                + "ls" + System.getProperty("line.separator")
                + "mkdir testDirectory" + System.getProperty("line.separator")
                + "ls" + System.getProperty("line.separator")
                + "ls -r" + System.getProperty("line.separator")
                + "cd testDirectory" + System.getProperty("line.separator")
                + "pwd" + System.getProperty("line.separator")
                + "cd .." + System.getProperty("line.separator")
                + "cd .." + System.getProperty("line.separator") // checks that no message is displayed
                + "pwd" + System.getProperty("line.separator")
                + "touch testFile" + System.getProperty("line.separator")
                + "ls" + System.getProperty("line.separator")
                + "uknown command xyz" + System.getProperty("line.separator")
                + "cd directory1Level1/directory1Level2" + System.getProperty("line.separator")
                + "pwd" + System.getProperty("line.separator")
                + "cd .." + System.getProperty("line.separator")
                + "cd .." + System.getProperty("line.separator")
                + "pwd" + System.getProperty("line.separator")
                + "cd directory1Level1/directory1Level2/nonExisting" + System.getProperty("line.separator")
                + "ls directory1Level1/directory1Level2" + System.getProperty("line.separator")
                + "quit" + System.getProperty("line.separator"));

        fsSimulation.simulation();

        Assert.assertEquals("/root" + System.getProperty("line.separator")
                        + "[/root/file1Level1, /root/file2Level1, /root/directory1Level1, /root/directory2Level1, /root/directoryNewLevel1]" + System.getProperty("line.separator")
                        + "[/root/file1Level1, /root/file2Level1, /root/directory1Level1, /root/directory2Level1, /root/directoryNewLevel1, /root/testDirectory]" + System.getProperty("line.separator")
                        + "/root" + System.getProperty("line.separator")
                        + "/root/file1Level1" + System.getProperty("line.separator")
                        + "/root/file2Level1" + System.getProperty("line.separator")
                        + "/root/directory1Level1" + System.getProperty("line.separator")
                        + "/root/directory1Level1/file1Level2" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2/file1Level3" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2/file2Level3" + System.getProperty("line.separator")
                        + "/root/directory1Level1/newFile" + System.getProperty("line.separator")
                        + "/root/directory2Level1" + System.getProperty("line.separator")
                        + "/root/directory2Level1/file2Level2" + System.getProperty("line.separator")
                        + "/root/directory2Level1/file3Level2" + System.getProperty("line.separator")
                        + "/root/directoryNewLevel1" + System.getProperty("line.separator")
                        + "/root/testDirectory" + System.getProperty("line.separator")
                        + "/root/testDirectory" + System.getProperty("line.separator")
                        + "/root" + System.getProperty("line.separator")
                        + "[/root/file1Level1, /root/file2Level1, /root/directory1Level1, /root/directory2Level1, /root/directoryNewLevel1, /root/testDirectory, /root/testFile]" + System.getProperty("line.separator")
                        + "Non valid command" + System.getProperty("line.separator")
                        + "/root/directory1Level1/directory1Level2" + System.getProperty("line.separator")
                        + "/root" + System.getProperty("line.separator")
                        + "Invalid path" + System.getProperty("line.separator")
                        + "[/root/directory1Level1/directory1Level2/file1Level3, /root/directory1Level1/directory1Level2/file2Level3]" + System.getProperty("line.separator")


                , outContent.toString());

    }


}
