public class Edge {
    private Node src;
    private Node dest;

    public Edge(Node src, Node dest) {
        this.src = src;
        this.dest = dest;
    }

    public Node getSource() {
        return src;
    }

    public Node getDestination() {
        return dest;
    }

    public String toString(){
        return src.toString() + " -> " + dest.toString();
    }
}