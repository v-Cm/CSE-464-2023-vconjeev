import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();

        operateGraph(graphManager);

        performGraphSearch(graphManager, new Node("A"), new Node("A"), Algorithm.BFS);
        performGraphSearch(graphManager, new Node("B"), new Node("A"), Algorithm.BFS);
        performGraphSearch(graphManager, new Node("A"), new Node("A"), Algorithm.DFS);
        performGraphSearch(graphManager, new Node("B"), new Node("A"), Algorithm.DFS);

        outputGraphics(graphManager, "part1/outputs/main/mainOutputGraphics.png", "png");
        outputGraphics(graphManager, "part1/outputs/main/mainOutputGraphics.svg", "svg");
        outputGraphics(graphManager, "part1/outputs/main/mainOutputGraphics.dot", "dot");
    }

    private static void operateGraph(GraphManager graphManager) throws IOException {
        graphManager.parseGraph("part1/testInput.dot");
        graphManager.toString();

        graphManager.outputGraph("part1/outputs/main/mainOutputGraph.txt");

        graphManager.addNode("F");
        graphManager.toString();

        graphManager.addNodes(new String[]{"E", "A", "D", "G", "I"});
        graphManager.toString();

        graphManager.addEdge("B", "F");
        graphManager.addEdge("D", "G");
        graphManager.toString();

        graphManager.addEdge("C", "A");
        graphManager.toString();

        graphManager.removeNode("F");
        graphManager.toString();

        graphManager.removeNodes(new String[]{"E", "D"});
        graphManager.toString();

        graphManager.removeEdge("D", "G");
        graphManager.toString();
    }

    private static void performGraphSearch(GraphManager graphManager, Node source, Node destination, Algorithm algorithm) {
        Path path = graphManager.GraphSearch(source, destination, algorithm);
        System.out.println(path != null ? "The path:" + path : "No path exists.");
    }

    private static void outputGraphics(GraphManager graphManager, String filePath, String format) throws IOException {
        graphManager.outputGraphics(filePath, format);
    }
}

//extract method refactoring