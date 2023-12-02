import java.util.LinkedList;
import java.util.List;

public class BFS extends GraphSearchTemplate {
    @Override
    protected void exploreNeighbors(String current, Node destinationNode) {
        List<Node> possibleDestinations = edgeMapping.getOrDefault(current, new LinkedList<>());
        for (Node eachDestination : possibleDestinations) {
            nodeQueue.add(eachDestination.toString());
            if (!parentMapping.containsKey(eachDestination.toString())) {
                parentMapping.put(eachDestination.toString(), current);
            }
        }
    }
}
