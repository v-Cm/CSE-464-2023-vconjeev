import java.util.*;

public class RandomWalk extends GraphSearchTemplate {

    @Override
    protected void exploreNeighbors(String current, Node destinationNode) {
        if (destinationFound) {
            return;
        }

        System.out.println("Current Path: " + createPath(current));

        List<Node> neighbors = edgeMapping.get(current);
        if (neighbors != null) {
            // select random neighbor
            Collections.shuffle(neighbors);

            for (Node neighbor : neighbors) {
                String neighborString = neighbor.toString();
                if (!visitedNodes.contains(neighborString)) {
                    parentMapping.put(neighborString, current);

                    if (neighborString.equals(destinationNode.toString())) {
                        destinationFound = true;
                        break;
                    }

                    visitedNodes.add(neighborString);
                    exploreNeighbors(neighborString, destinationNode);
                }
            }
        }
    }
}
