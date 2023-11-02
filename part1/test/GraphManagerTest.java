import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GraphManagerTest {
    static GraphManager graphManager;
    private static String TEST_FILE_NAME = "testInput.dot";
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

        // test SVG file
        graphManager.outputGraphics(TEST_OUTPUT_GRAPHICS_SVG_FILE, "svg");
        output = Files.readAllLines(Paths.get(TEST_OUTPUT_GRAPHICS_SVG_FILE), StandardCharsets.UTF_8);
        expected = Files.readAllLines(Paths.get(EXPECTED_OUTPUT_GRAPHICS_SVG_FILE), StandardCharsets.UTF_8);

        // Normalize line endings to ensure consistent comparison
        for (int i = 0; i < expected.size(); i++) {
            expected.set(i, expected.get(i).replaceAll("\\r", ""));
        }
        for (int i = 0; i < output.size(); i++) {
            output.set(i, output.get(i).replaceAll("\\r", ""));
        }

        Assert.assertEquals(expected, output);

        // test PNG file
        graphManager.outputGraphics(TEST_OUTPUT_GRAPHICS_PNG_FILE, "png");
        BufferedImage expectedImage = ImageIO.read(new File(EXPECTED_OUTPUT_GRAPHICS_PNG_FILE));
        BufferedImage actualImage = ImageIO.read(new File(TEST_OUTPUT_GRAPHICS_PNG_FILE));
        Assert.assertEquals(0, checkImagesEqual(expectedImage, actualImage));
    }
}
