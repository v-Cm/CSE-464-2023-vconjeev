import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();
        GraphManager graphManager1 = new GraphManager();

        operateGraph(graphManager, graphManager1);

        performGraphSearch(graphManager, new Node("A"), new Node("A"), Algorithm.BFS);
        performGraphSearch(graphManager, new Node("B"), new Node("A"), Algorithm.BFS);
        performGraphSearch(graphManager, new Node("A"), new Node("A"), Algorithm.DFS);
        performGraphSearch(graphManager, new Node("B"), new Node("A"), Algorithm.DFS);
        performGraphSearch(graphManager1, new Node("a"), new Node("c"), Algorithm.RANDOMWALK);

        outputGraphics(graphManager, "part1/outputs/main/mainOutputGraphics.png", "png");
        outputGraphics(graphManager, "part1/outputs/main/mainOutputGraphics.svg", "svg");
        outputGraphics(graphManager, "part1/outputs/main/mainOutputGraphics.dot", "dot");
    }

    private static void operateGraph(GraphManager graphManager, GraphManager graphManager1) throws IOException {
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

        graphManager1.parseGraph("part1/input2.dot");
        graphManager1.toString();

    }

    private static void performGraphSearch(GraphManager graphManager, Node source, Node destination, Algorithm algorithm) {
        Path path = graphManager.GraphSearch(source.toString(), destination.toString(), algorithm);
        System.out.println(path != null ? "The path:" + path : "No path exists.");
    }

    private static void outputGraphics(GraphManager graphManager, String filePath, String format) throws IOException {
        graphManager.outputGraphics(filePath, format);
    }
}

//extract method refactoring