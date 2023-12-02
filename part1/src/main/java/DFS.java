import java.util.LinkedList;
import java.util.List;

public class DFS extends GraphSearchTemplate {
    @Override
    protected void exploreNeighbors(String current, Node destinationNode) {
        if (destinationFound) {
            return;
        }

        List<Node> neighbors = edgeMapping.get(current);
        if (neighbors != null) {
            for (Node neighbor : neighbors) {
                String neighborString = neighbor.toString();
                if (!visitedNodes.contains(neighborString)) {
                    parentMapping.put(neighborString, current);

                    if (neighborString.equals(destinationNode.toString())) {
                        destinationFound = true;
                        break;
                    }

                    exploreNeighbors(neighborString, destinationNode);
                }
            }
        }
    }
}
