import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GraphManagerTest {
    static GraphManager graphManager;
    static GraphManager graphManager1;
    private static String TEST_FILE_NAME = "testInput.dot";
    private static String TEST_RW_FILE = "input2.dot";
    public static final String EXPECTED_TO_STRING = "Number of Nodes: 3\n" +
            "Label of Nodes: [A, B, C]\n" +
            "Number of Edges: 3\n" +
            "Nodes and Edge Directions:\n" +
            "C -> A\n" +
            "B -> C\n" +
            "A -> B";
    public static final String TEST_OUTPUT_GRAPH_FILE = "outputs/test/testOutputGraph.txt";
    private static final String TEST_OUTPUT_GRAPHICS_PNG_FILE = "outputs/test/testOutputGraphics.png";
    private static final String TEST_OUTPUT_GRAPHICS_SVG_FILE = "outputs/test/testOutputGraphics.svg";
    private static final String TEST_OUTPUT_GRAPHICS_DOT_FILE = "outputs/test/testOutputGraphics.dot";

    private static final String EXPECTED_OUTPUT_GRAPH_FILE = "outputs/expected/expectedOutputGraph.txt";
    private static final String EXPECTED_OUTPUT_GRAPHICS_PNG_FILE = "outputs/expected/expectedOutputGraphics.png";
    private static final String EXPECTED_OUTPUT_GRAPHICS_SVG_FILE = "outputs/expected/expectedOutputGraphics.svg";
    private static final String EXPECTED_OUTPUT_GRAPHICS_DOT_FILE = "outputs/expected/expectedOutputGraphics.dot";

    @Before
    public void setUp() throws Exception {
        graphManager = new GraphManager();
        graphManager.parseGraph(TEST_FILE_NAME);
    }

    @Test
    public void testParseGraph() {
        Assert.assertEquals(3, graphManager.nodeSize());
        Assert.assertEquals(3, graphManager.edgeSize());
        Assert.assertTrue(graphManager.containsEdge("C", "A"));
        Assert.assertTrue(graphManager.containsEdge("A", "B"));
        Assert.assertTrue(graphManager.containsEdge("B", "C"));
        Assert.assertFalse(graphManager.containsNode("F"));
    }

    @Test
    public void testToString() {
        String actual = graphManager.toString();
        Assert.assertEquals(EXPECTED_TO_STRING, actual);
    }

    @Test
    public void testOutputGraph() throws IOException {
        graphManager.outputGraph(TEST_OUTPUT_GRAPH_FILE);
        List<String> actualLines = Files.readAllLines(Paths.get(TEST_OUTPUT_GRAPH_FILE));
        List<String> expectedLines = Files.readAllLines(Paths.get(EXPECTED_OUTPUT_GRAPH_FILE));
        Assert.assertEquals(expectedLines, actualLines);
    }

    @Test
    public void testAddNode(){
        Assert.assertEquals(3, graphManager.nodeSize());
        graphManager.addNode("A");
        Assert.assertEquals(3, graphManager.nodeSize());
        graphManager.addNode("F");
        Assert.assertEquals(4, graphManager.nodeSize());
        graphManager.addNode("B");
        Assert.assertEquals(4, graphManager.nodeSize());
    }

    @Test
    public void testAddNodes(){
        Assert.assertEquals(3, graphManager.nodeSize());
        graphManager.addNodes(new String[]{"C","E","F"});
        Assert.assertEquals(5, graphManager.nodeSize());
        graphManager.addNodes(new String[]{"D","G","I"});
        Assert.assertEquals(8, graphManager.nodeSize());
    }

    @Test
    public void testRemoveNode(){
        Assert.assertEquals(3, graphManager.nodeSize());
        graphManager.removeNode("A");
        Assert.assertEquals(2, graphManager.nodeSize());
        graphManager.removeNode("C");
        Assert.assertEquals(1, graphManager.nodeSize());
        Assert.assertEquals(0, graphManager.edgeSize());
    }

    @Test
    public void testRemoveNodes(){
        Assert.assertEquals(3, graphManager.nodeSize());
        graphManager.removeNodes(new String[]{"A","C","F"});
        Assert.assertEquals(1, graphManager.nodeSize());
        graphManager.removeNodes("B","H "," P ");
        Assert.assertEquals(0, graphManager.nodeSize());
    }

    @Test
    public void testAddEdge(){
        Assert.assertEquals(3, graphManager.edgeSize());
        graphManager.addEdge("B", "F");
        Assert.assertEquals(4, graphManager.edgeSize());
        graphManager.addEdge("D", "G");
        Assert.assertEquals(5, graphManager.edgeSize());
        graphManager.addEdge("C","A");
        Assert.assertEquals(5, graphManager.edgeSize());
    }

    @Test
    public void testRemoveEdge(){
        Assert.assertEquals(3, graphManager.edgeSize());
        graphManager.removeEdge("B", "C");
        Assert.assertEquals(2, graphManager.edgeSize());
        graphManager.removeEdge(" A  ", "P");
        Assert.assertEquals(2, graphManager.edgeSize());
    }

    // function to compare the test image and expected image of the graph
    private long checkImagesEqual(BufferedImage expectedImage, BufferedImage actualImage) {
        int w = expectedImage.getWidth();
        int h = expectedImage.getHeight();
        long difference = 0;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int expectedPixel = expectedImage.getRGB(j, i);
                int actualPixel = actualImage.getRGB(j, i);

                difference += Math.abs((expectedPixel >> 16) & 0xff - (actualPixel >> 16) & 0xff) +
                              Math.abs((expectedPixel >> 8) & 0xff - (actualPixel >> 8) & 0xff) +
                              Math.abs(expectedPixel & 0xff - actualPixel & 0xff);
            }
        }

        return ((difference / ((long) w * h * 3)) / 255);
    }

    @Test
    public void testOutputGraphics() throws IOException {
        List<String> output, expected;

        graphManager.addNode("F");
        graphManager.addEdge("B", "F");

        // test DOT file
        graphManager.outputGraphics(TEST_OUTPUT_GRAPHICS_DOT_FILE, "dot");
        output = Files.readAllLines(Paths.get(TEST_OUTPUT_GRAPHICS_DOT_FILE));
        expected = Files.readAllLines(Paths.get(EXPECTED_OUTPUT_GRAPHICS_DOT_FILE));
        Assert.assertEquals(expected, output);

        // test PNG file
        graphManager.outputGraphics(TEST_OUTPUT_GRAPHICS_PNG_FILE, "png");
        BufferedImage expectedImage = ImageIO.read(new File(EXPECTED_OUTPUT_GRAPHICS_PNG_FILE));
        BufferedImage actualImage = ImageIO.read(new File(TEST_OUTPUT_GRAPHICS_PNG_FILE));
        Assert.assertEquals(0, checkImagesEqual(expectedImage, actualImage));
    }

    @Test
    public void testGraphSearchBFS() {
        Assert.assertEquals("A", graphManager.GraphSearch("A", "A", Algorithm.BFS).toString());
        Assert.assertEquals("B -> C -> A", graphManager.GraphSearch("B", "A", Algorithm.BFS).toString());

        graphManager.addNodes(new String[]{"D", "E", "F"});
        graphManager.addEdge("C", "F");
        graphManager.addEdge("D", "E");
        graphManager.addEdge("F", "D");

        Assert.assertEquals("A -> B -> C -> F -> D -> E", graphManager.GraphSearch("A", "E", Algorithm.BFS).toString());

        graphManager.addNode("X");
        Assert.assertNull(graphManager.GraphSearch("A", "X", Algorithm.BFS));
        Assert.assertNull(graphManager.GraphSearch("F", "B", Algorithm.BFS));
        Assert.assertNull(graphManager.GraphSearch("E", "S", Algorithm.BFS));
        Assert.assertNull(graphManager.GraphSearch("B", "A", Algorithm.INVALID));
    }

    @Test
    public void testGraphSearchDFS() {
        Assert.assertEquals("B", graphManager.GraphSearch("B", "B", Algorithm.DFS).toString());
        Assert.assertEquals("C -> A -> B", graphManager.GraphSearch("C", "B", Algorithm.DFS).toString());

        graphManager.addNodes(new String[]{"T", "R", "P"});
        graphManager.addEdge("C", "P");
        graphManager.addEdge("T", "R");
        graphManager.addEdge("P", "T");

        Assert.assertEquals("A -> B -> C -> P -> T -> R", graphManager.GraphSearch("A", "R", Algorithm.DFS).toString());

        graphManager.addNode("M");
        Assert.assertNull(graphManager.GraphSearch("A", "M", Algorithm.DFS));
        Assert.assertNull(graphManager.GraphSearch("P", "B", Algorithm.DFS));
        Assert.assertNull(graphManager.GraphSearch("R", "S", Algorithm.DFS));
        Assert.assertNull(graphManager.GraphSearch("B", "A", Algorithm.INVALID));
    }

    @Before
    public void setUp2() throws IOException {
        graphManager1 = new GraphManager();
        graphManager1.parseGraph(TEST_RW_FILE);
    }

    @Test
    public void testGraphSearchRandomWalk(){
        Assert.assertEquals("a -> b -> c", graphManager1.GraphSearch("a", "c", Algorithm.RANDOMWALK).toString());
        Assert.assertNull(graphManager1.GraphSearch("h", "a", Algorithm.RANDOMWALK));
    }
}
