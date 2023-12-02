import java.util.*;

public abstract class GraphSearchTemplate {
    protected Map<String, List<Node>> edgeMapping;
    protected Set<String> visitedNodes;
    protected Map<String, String> parentMapping;
    protected Queue<String> nodeQueue;

    boolean destinationFound = false;

    public GraphSearchTemplate() {
        edgeMapping = new HashMap<>();
        visitedNodes = new HashSet<>();
        parentMapping = new HashMap<>();
        nodeQueue = new LinkedList<>();
    }

    protected abstract void exploreNeighbors(String current, Node destinationNode);

    protected Path search(String sourceNode, String destinationNode) {
        parentMapping.put(sourceNode, null);
        nodeQueue.add(sourceNode);

        while (!nodeQueue.isEmpty()) {
            String current = nodeQueue.poll();
            if (!visitedNodes.contains(current)) {
                visitedNodes.add(current);

                if (current.equals(destinationNode)) {
                    break;
                }

                exploreNeighbors(current, new Node(destinationNode));

                if(destinationFound){
                    break;
                }
            }
        }

        return createPath(destinationNode);
    }

    protected Path createPath(String destinationNode) {
        Path path = null;
        if (parentMapping.containsKey(destinationNode)) {
            path = new Path();
            path.addNodeInTheFront(new Node(destinationNode));
            String parent = parentMapping.get(destinationNode);
            while (parent != null) {
                path.addNodeInTheFront(new Node(parent));
                parent = parentMapping.get(parent);
            }
        }
        return path;
    }
}
