import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Path {
    private final LinkedList<Node> nodesInPath;

    public Path() {
        nodesInPath = new LinkedList<>();
    }

    public void addNodeInTheFront(Node node){
        nodesInPath.addFirst(node);
    }

    @Override
    public String toString() {
        return nodesInPath.stream()
                .map(Node::toString)
                .collect(Collectors.joining(" -> "));
    }
}
