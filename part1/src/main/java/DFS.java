import java.util.LinkedList;
import java.util.List;

public class DFS extends GraphSearchTemplate {
    @Override
    protected void exploreNeighbors(String current, Node destinationNode) {
        List<Node> possibleDestinations = edgeMapping.getOrDefault(current, new LinkedList<>());
        for (Node eachDestination : possibleDestinations) {
            if (!visitedNodes.contains(eachDestination.toString())) {
                nodeQueue.add(eachDestination.toString());
                if (!parentMapping.containsKey(eachDestination.toString())) {
                    parentMapping.put(eachDestination.toString(), current);
                }
            }
        }
    }
}
