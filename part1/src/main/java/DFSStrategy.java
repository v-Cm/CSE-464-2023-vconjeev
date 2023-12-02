public class DFSStrategy implements GraphSearchStrategy {
    @Override
    public Path search(Graph graph, Node sourceNode, Node destinationNode) {
        DFS dfs = new DFS();
        dfs.edgeMapping = graph.getEdgeMapping();
        return dfs.search(sourceNode.toString(), destinationNode.toString());
    }
}