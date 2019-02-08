
import java.util.ArrayList;
import java.util.*;

public class Graph {
    private ArrayList<Node> vertices; //this is a list of all vertices, populated by Driver class.
    private Heap minHeap;    //this is the heap to use for Dijkstra
    private Node[] parentTable; //the index represents each Node's name, the value represents the parent Node's name

    public Graph(int numVertices) {
        minHeap = new Heap();
        vertices = new ArrayList<Node>();
        parentTable = new Node[numVertices]; // the size of parentTable is equal to size of input vertices
        // feel free to add anything else you may want
    }


    // findShortestPathLength
    //
    // Returns the distance of the shortest path from root to x
    public int findShortestPathLength(Node root, Node x) {
        ArrayList<Node> EveryShortestPath = findEveryShortestPathLength(root); //find every shortest path from root
        int position = EveryShortestPath.indexOf(x);   //find position of node x in every shortest path
        int shortestPathLength = EveryShortestPath.get(position).getMinDistance(); //get shortest path of node x
        if (shortestPathLength == Integer.MAX_VALUE )   //if root can not reach node x
            shortestPathLength = -1;    //make it -1 ,which is by requirement
        return shortestPathLength;
    }

    // findAShortestPath
    //
    // Returns a list of nodes represent one of the shortest paths
    // from root to x
    public ArrayList<Node> findAShortestPath(Node root, Node x) {
        findEveryShortestPathLength(root);
        ArrayList<Node> shortestPath = new ArrayList<>();
        if(root==x){
            shortestPath.add(root);
            return  shortestPath;
        }
        Node parent = parentTable[x.getNodeName()];     //get the parent of node x from parentTable
        if (parent == x)   //if parent =itself ,which means there is no path from root to node x.
            return null;
        shortestPath.add(x);
        shortestPath.add(parent);
        while (parent != root) {   // keep find parent of current parent until we reach root
            if (parent == parentTable[parent.getNodeName()])
                return null;
            else
                parent = parentTable[parent.getNodeName()];
            shortestPath.add(parent);
        }
        Collections.reverse(shortestPath);  //reverse the path because we add it in reverse order.
        return shortestPath;
    }

    // eachShortestPathLength
    //
    // Returns an ArrayList of Nodes, where minDistance of each node is the
    // length of the shortest path from it to the root. This ArrayList
    // should contain every Node in the graph. Note that
    // root.getMinDistance() = 0
    public ArrayList<Node> findEveryShortestPathLength(Node root) {
        root.setMinDistance(0);      //first set root' minimal distance to 0
        minHeap.buildHeap(vertices); //build a heap using input vertices  *Note: the vertices are initialized by driver
        for (int i = 0; i < vertices.size(); i++) {
            parentTable[vertices.get(i).getNodeName()] = vertices.get(i);       //initialize every node'parent as itself
        }
        ArrayList<Node> solution = new ArrayList<>();
        while (!minHeap.is_empty()) {
            Node v = minHeap.extractMin();  //extract min distance Node from heap
            solution.add(v);
            if (minHeap.is_empty())    //the last node does need to relax
                break;
            ArrayList<Node> u = v.getNeighbors();
            ArrayList<Integer> weight = v.getWeights();
            if (v.getMinDistance() != Integer.MAX_VALUE) {
                for (int i = 0; i < u.size(); i++) {       //relax procedures
                    if (minHeap.checkExistence(u.get(i))) {  //if a node is not in heap, there is no need to relax on it.
                        if (minHeap.getNode(minHeap.getIndex(u.get(i))).getMinDistance() > (v.getMinDistance() + weight.get(i))) {
                            minHeap.getNode(minHeap.getIndex(u.get(i))).setMinDistance(v.getMinDistance() + weight.get(i)); //set minimum distance
                            parentTable[u.get(i).getNodeName()] = v;   //set parent
                            minHeap.Heapify_Up(minHeap.getIndex(u.get(i))); //call heapify_up to rearrange heap.
                        }
                    }
                }
            }
        }
        for(int x=0;x<solution.size();x++){
            if(solution.get(x).getMinDistance()==Integer.MAX_VALUE)
                solution.get(x).setMinDistance(-1);
        }
        return solution;
    }

    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (Node v : vertices) {
            boolean first = true;
            o += "Node ";
            o += v.getNodeName();
            o += " has neighbors: ";
            ArrayList<Node> ngbr = v.getNeighbors();
            for (Node n : ngbr) {
                o += first ? n.getNodeName() : ", " + n.getNodeName();
                first = false;
            }
            first = true;
            o += " with weights ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<Node> getAllNodes() {
        return vertices;
    }

    //used by Driver class to populate each Node with correct neighbors and corresponding weights
    public void setEdge(Node curr, Node neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    //This is used by Driver.java and sets vertices to reference an ArrayList of all nodes.
    public void setAllNodesArray(ArrayList<Node> x) {
        vertices = x;
    }
}
