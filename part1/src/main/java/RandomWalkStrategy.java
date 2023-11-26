public class RandomWalkStrategy implements GraphSearchStrategy {
    @Override
    public Path search(Graph graph, Node sourceNode, Node destinationNode) {
        RandomWalk randomWalk = new RandomWalk();
        randomWalk.edgeMapping = graph.getEdgeMapping();
        return randomWalk.search(sourceNode.toString(), destinationNode.toString());
    }
}
