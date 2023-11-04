import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();

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

//        graphManager.removeNode("F");
//        graphManager.toString();
//
//        graphManager.removeNodes(new String[]{"E", "A"});
//        graphManager.toString();
//
//        graphManager.removeEdge("D", "G");
//        graphManager.toString();

        Path path = graphManager.GraphSearch(new Node("A"), new Node("A"));
        System.out.println(path != null ? "The path:" + path : "No path exists.");

        path = graphManager.GraphSearch(new Node("B"), new Node("A"));
        System.out.println(path != null ? "The path:" + path : "No path exists.");

        Path path = graphManager.GraphSearch(new Node("A"), new Node("A"));
        System.out.println(path != null ? "The path:" + path : "No path exists.");

        path = graphManager.GraphSearch(new Node("B"), new Node("A"));
        System.out.println(path != null ? "The path:" + path : "No path exists.");

        graphManager.outputGraphics("part1/outputs/main/mainOutputGraphics.png", "png");
        graphManager.outputGraphics("part1/outputs/main/mainOutputGraphics.svg", "svg");
        graphManager.outputGraphics("part1/outputs/main/mainOutputGraphics.dot", "dot");
    }
}
