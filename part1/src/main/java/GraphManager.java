import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.*;
import java.nio.file.Files;

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
        output.append("Nodes and Edge Directions: \n");
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
}
