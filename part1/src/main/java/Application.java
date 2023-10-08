import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();

        graphManager.parseGraph("testInput.dot");
        graphManager.toString();

        graphManager.addNode("D");
        graphManager.toString();

        graphManager.addNodes(new String[]{"E", "A", "F"});
        graphManager.toString();

        graphManager.addEdge("B", "F");
        graphManager.toString();

        graphManager.addEdge("C", "A");
        graphManager.toString();

        graphManager.outputGraph("output.txt");

        graphManager.outputGraphics("outputGraphics.png", "png");
    }
}
