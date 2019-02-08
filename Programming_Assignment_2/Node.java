import java.util.*;

public class Node {

    private int minDistance;
    private int nodeName;
    private ArrayList<Node> neighbors;
    private ArrayList<Integer> weights;

    public Node(int x) {
        nodeName = x;
        minDistance = Integer.MAX_VALUE;
        neighbors = new ArrayList<Node>();
        weights = new ArrayList<Integer>();
    }

    public Node(int x, int y) {
        nodeName = x;
        minDistance = y;
    }

    public void setNeighborAndWeight(Node n, Integer w) {
        neighbors.add(n);
        weights.add(w);
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Integer> getWeights() {
        return weights;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int x) {
        minDistance = x;
    }

    public int getNodeName() {
        return nodeName;
    }
}
