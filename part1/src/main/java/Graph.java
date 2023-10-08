import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

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
            createEdge(edge.from().name().toString(), edge.to().name().toString());
        }
    }

    public int nodeSize(){
        return nodes.size();
    }
    public int edgeSize() {
        return edges.size();
    }

    public List<String> getAllNodes() {
        return new ArrayList<>(nodes.keySet());
    }

    public String toString(){
        return edges.values().stream().map(Edge::toString).collect(Collectors.joining("\n"));
    }

    public void addNode(String label) {
        if(!nodes.containsKey(label)){
            nodes.put(label, new Node(label));
        }else{
            System.out.println("Node: " + label + " already exists! Cannot add.");
        }
    }

    public void addNodes(String[] labels) {
        Arrays.stream(labels).forEach(this::addNode);
    }

    private void createEdge(String srcLabel, String dstLabel) {
        Node src = nodes.get(srcLabel);
        Node dst = nodes.get(dstLabel);
        edges.putIfAbsent(srcLabel + "->" + dstLabel, new Edge(src, dst));
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if(!edges.containsKey(srcLabel + "->" + dstLabel)){
            createEdge(srcLabel, dstLabel);
        }else{
            System.out.println("Edge already exist from " + srcLabel + " to " + dstLabel + "!");
        }
    }

    public MutableGraph convertToGraphViz(){
        MutableGraph g = mutGraph(name).setDirected(true);
        nodes.values().forEach(each -> g.add(mutNode(each.toString())));
        edges.values().forEach(each -> g.add(mutNode(each.getSource().toString()).addLink(mutNode(each.getDestination().toString()))));
        return g;
    }

}
