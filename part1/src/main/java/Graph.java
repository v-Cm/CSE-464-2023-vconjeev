import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private String name;
    private Map<String, Node> nodes;
    private Map<String, Edge> edges;

    public Graph(MutableGraph mutableGraph) {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        this.name = mutableGraph.name().toString();

        for (MutableNode each : mutableGraph.nodes()) {
            String nodeName = each.name().toString();
            nodes.putIfAbsent(nodeName, new Node(nodeName));
        }

        for (Link edge : mutableGraph.edges()) {
            Node src = nodes.get(edge.from().name().toString());
            Node dst = nodes.get(edge.to().name().toString());
            edges.putIfAbsent(Edge.edgeString(src, dst), new Edge(src, dst));
        }
    }

    public int nodeSize(){
        return nodes.keySet().size();
    }
    public int edgeSize() {
        return edges.keySet().size();
    }

    public List<String> getAllNodes() {
        return nodes.values().stream().map(Node::getName).collect(Collectors.toList());
    }

    public String toString(){
        StringBuilder str_build = new StringBuilder();
        for (Edge e : edges.values()){
            str_build.append(e.toString()).append("\n");
        }
        return str_build.toString();
    }

    public void addNode(String label) {
        if(!nodes.containsKey(label)){
            nodes.put(label, new Node(label));
        }else{
            System.out.println("Node: " + label + " already exists! Cannot add.");
        }
    }

    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

}
