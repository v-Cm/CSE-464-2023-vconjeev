public class Edge {
    private Node src;
    private Node dest;

    public Edge(Node src, Node dest) {
        this.src = src;
        this.dest = dest;
    }

    public static String edgeString(Node src, Node dest){
        return new StringBuilder().append(src.getName()).append("->").append(dest.getName()).toString();
    }

    public String toString(){
        return src.toString() + " -> " + dest.toString();
    }
}