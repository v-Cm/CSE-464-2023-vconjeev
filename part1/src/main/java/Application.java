import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();

        graphManager.parseGraph("testInput.dot");
        graphManager.toString();

        graphManager.addNode("F");
        graphManager.toString();

        graphManager.addNodes(new String[]{"E", "A", "F", "G", "I"});
        graphManager.toString();

        graphManager.addEdge("B", "F");
        graphManager.addEdge("D", "G");
        graphManager.toString();

        graphManager.addEdge("C", "A");
        graphManager.toString();

        graphManager.outputGraph("mainOutputGraph.txt");

        graphManager.outputGraphics("mainOutputGraphics.png", "png");
        graphManager.outputGraphics("mainOutputGraphics.svg", "svg");
        graphManager.outputGraphics("mainOutputGraphics.dot", "dot");
    }
}
