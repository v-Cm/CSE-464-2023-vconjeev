import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        GraphManager graphManager = new GraphManager();
        graphManager.parseGraph("testInput.dot");
        graphManager.toString();
        graphManager.outputGraph("output.txt");
    }
}
