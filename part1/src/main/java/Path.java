import java.util.LinkedList;
import java.util.stream.Collectors;

public class Path {
    private final LinkedList<Node> nodesInPath;

    public Path() {
        nodesInPath = new LinkedList<>();
    }

    public void addNodeInTheEnd(Node node){
        nodesInPath.add(node);
    }

    public Node delLastNode(){
        return nodesInPath.removeLast();
    }
    @Override
    public String toString() {
        return nodesInPath.stream()
                .map(Node::toString)
                .collect(Collectors.joining(" -> "));
    }
}
