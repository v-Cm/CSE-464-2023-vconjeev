import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class GraphManager {
    Graph graph;
    private boolean printToConsole = true;

    public void parseGraph(String fileName) throws IOException {
        File initialFile = new File(fileName);
        InputStream dot = new DataInputStream(Files.newInputStream(initialFile.toPath()));
        MutableGraph mutableGraph = new Parser().read(dot);
        graph = new Graph(mutableGraph);
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("Number of Nodes: ").append(graph.nodeSize()).append("\n");
        output.append("Label of Nodes: ").append(graph.getAllNodes()).append("\n");
        output.append("Number of Edges: ").append(graph.edgeSize()).append("\n");
        output.append("Nodes and Edge Directions:\n");
        output.append(graph.toString());

        if (printToConsole) {
            System.out.println(output);
        }

        return output.toString();
    }

    public void outputGraph(String outputPath) {
        try {
            File file = new File(outputPath);
            FileWriter writer = new FileWriter(file);
            printToConsole = false;
            writer.write(toString());
            printToConsole = true;
            writer.close();

            System.out.println("Graph output written to file: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNode(String label){
        graph.addNode(label);
    }

    public void addNodes(String[] label){
        graph.addNodes(label);
    }

    public void addEdge(String srcLabel, String dstLabel) {
        graph.addEdge(srcLabel, dstLabel);
    }

    public void outputGraphics(String path, String format) throws IOException {
        MutableGraph mutGraph = graph.convertToGraphViz();
        Format renderFormat = null;

        switch (format.toLowerCase()) {
            case "png":
                renderFormat = Format.PNG;
                break;
            case "svg":
                renderFormat = Format.SVG;
                break;
            case "dot":
                renderFormat = Format.DOT;
                break;
            default:
                System.out.println("Unsupported format: " + format);
                return;
        }
        Graphviz.fromGraph(mutGraph).width(900).render(renderFormat).toFile(new File(path));
        System.out.println("Graphic output available in file: " + path);
    }
    public int nodeSize(){
        return graph.nodeSize();
    }

    public boolean containsNode(String nodeName) {
        return graph.containsNode(nodeName);
    }

    public int edgeSize() {
        return graph.edgeSize();
    }

    public boolean containsEdge(String src, String dst) {
        return graph.containsEdge(src, dst);
    }

    public void removeNode(String label){
        graph.removeNode(label);
    }

    public void removeNodes(String... label){
        graph.removeNodes(label);
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        graph.removeEdge(srcLabel, dstLabel);
    }

    public Path GraphSearch(Node src, Node dst, Algorithm algo) {
        if(algo == Algorithm.BFS)
            return graph.findPathUsingBFS(src, dst);
        else if (algo == Algorithm.DFS)
            return graph.findPathUsingDFS(src, dst);
        else
            return null;
    }
}
