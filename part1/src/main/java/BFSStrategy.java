public class BFSStrategy implements GraphSearchStrategy {
    @Override
    public Path search(Graph graph, Node sourceNode, Node destinationNode) {
        BFS bfs = new BFS();
        bfs.edgeMapping = graph.getEdgeMapping();
        return bfs.search(sourceNode.toString(), destinationNode.toString());
    }
}