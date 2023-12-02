public class GraphSearchContext {
    private GraphSearchStrategy strategy;

    public void setStrategy(GraphSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public Path executeSearch(Graph graph, Node sourceNode, Node destinationNode) {
        if (strategy == null) {
            System.out.println("Strategy not set.");
            return null;
        }
        return strategy.search(graph, sourceNode, destinationNode);
    }
}
