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

    private static final String EDGE_SEPARATOR = "->";

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

        String edgeKey = srcLabel + EDGE_SEPARATOR + dstLabel;

        edges.putIfAbsent(edgeKey, new Edge(src, dst));
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if(!edges.containsKey(srcLabel + EDGE_SEPARATOR + dstLabel)){
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

    public boolean containsNode(String nodeName) {
        return nodes.containsKey(nodeName);
    }

    public boolean containsEdge(String src, String dst) {
        return edges.containsKey(src + EDGE_SEPARATOR + dst);
    }

    public void removeNode(String nodeName) {
        nodeName = nodeName.trim();
        if (nodes.remove(nodeName) != null) {
            removeAssociatedEdges(nodeName);
        } else {
            System.out.println("Node " + nodeName + " does not exist!");
        }
    }

    private void removeAssociatedEdges(String nodeName) {
        String finalNodeName = nodeName;
        List<String> edgesToBeRemoved = edges.values().stream()
                .filter(edge -> edge.getSource().toString().equals(finalNodeName)
                        || edge.getDestination().toString().equals(finalNodeName))
                .map(edge -> (edge.getSource() + EDGE_SEPARATOR + edge.getDestination()))
                .collect(Collectors.toList());
        edgesToBeRemoved.forEach(each -> edges.remove(each));
    }

    public void removeNodes(String[] nodeNames){
        for (String each : nodeNames) {
            removeNode(each);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        srcLabel = srcLabel.trim();
        dstLabel = dstLabel.trim();
        String edgeKey = srcLabel + EDGE_SEPARATOR + dstLabel;
        if (edges.containsKey(edgeKey)) {
            edges.remove(edgeKey);
        } else {
            System.out.println("No such edge exist from " + srcLabel + " to " + dstLabel);

        }
    }
  
    public Map<String, List<Node>> getEdgeMapping() {
        Map<String, List<Node>> edgeMapping = new HashMap<>();
        if (!edges.values().isEmpty()){
            for (Edge eachEdge : edges.values()) {
                List<Node> list = edgeMapping.getOrDefault(eachEdge.getSource().toString(), new LinkedList<>());
                list.add(eachEdge.getDestination());
                edgeMapping.put(eachEdge.getSource().toString(), list);
            }
        }
        return edgeMapping;
    }
}
//extract variable on "->"
//extract method on findPathUsingBFS
//extract method removeNode
//extract variable on createEdge