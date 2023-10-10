import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();

        graphManager.parseGraph("testInput.dot");
        graphManager.toString();

        graphManager.outputGraph("outputs/main/mainOutputGraph.txt");

        graphManager.addNode("F");
        graphManager.toString();

        graphManager.addNodes(new String[]{"E", "A", "D", "G", "I"});
        graphManager.toString();

        graphManager.addEdge("B", "F");
        graphManager.addEdge("D", "G");
        graphManager.toString();

        graphManager.addEdge("C", "A");
        graphManager.toString();

        graphManager.outputGraphics("outputs/main/mainOutputGraphics.png", "png");
        graphManager.outputGraphics("outputs/main/mainOutputGraphics.svg", "svg");
        graphManager.outputGraphics("outputs/main/mainOutputGraphics.dot", "dot");
    }
}
