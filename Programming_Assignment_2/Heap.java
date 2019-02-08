
import java.util.ArrayList;


public class Heap {

    private ArrayList<Node> minHeap; // do not remove
    private ArrayList<Integer> position; //index represents the position ,and the value indicates Node's name

    public Heap() {
        minHeap = new ArrayList<Node>(); // do not remove
        position = new ArrayList<Integer>();
    }

    public void Heapify_down(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int Size = minHeap.size();
        int smallerChild=left;
        if (i >= (Size / 2)) //a leaf is a heap,no need to swap. this is the end condition
            return;
        if (Size == 2 && minHeap.get(0).getMinDistance() > minHeap.get(1).getMinDistance()) //if there is only two nodes, just compare them and swap if needed.
            swap(0, 1);
        if (left < Size && right < Size) {     //check whether the range is satisfied
            if (minHeap.get(left).getMinDistance() > minHeap.get(right).getMinDistance()) //determine smaller child
                smallerChild = right;
            else if (minHeap.get(left).getMinDistance() < minHeap.get(right).getMinDistance())
                smallerChild = left;
            else {
                if (minHeap.get(left).getNodeName() < minHeap.get(right).getNodeName())
                    smallerChild = left;
                else
                    smallerChild = right;
            }
        }
        if (left < Size && right >= Size)
            smallerChild = left;

        if (minHeap.get(i).getMinDistance() > minHeap.get(smallerChild).getMinDistance()) { //compare with smaller child
            swap(smallerChild, i);
            Heapify_down(smallerChild);
        }
    }


    public void Heapify_Up(int i) {
        if (i == 0)  //end condition
            return;
        int parent = (i - 1) / 2;
        if (minHeap.get(parent).getMinDistance() > minHeap.get(i).getMinDistance()) { //if parent is larger
            swap(parent, i);
            Heapify_Up(parent);
        }
    }

    private void swap(int position1, int position2) { //swap does two things, swap two nodes and their corresponding position Array list.
        Node temp = minHeap.get(position1);
        minHeap.set(position1, minHeap.get(position2));
        minHeap.set(position2, temp);
        int tempNum = position.get(position1);
        position.set(position1, position.get(position2));
        position.set(position2, tempNum);
    }

    public boolean is_empty() {
        if (minHeap.size() == 0)
            return true;
        return false;
    }

    public Node getNode(int index) {
        return minHeap.get(index);
    }

    public int getIndex(Node x) {
        return position.indexOf(x.getNodeName());
    }

    public boolean checkExistence(Node x) {
        for (int i = 0; i < minHeap.size(); i++) {
            if (minHeap.get(i).getNodeName() == x.getNodeName())
                return true;
        }
        return false;
    }


    // buildHeap
    //
    // Given an ArrayList of Nodes, build a minimum heap keyed on each
    // Node's minDistance
    //
    // Time Complexity Requirement: theta(n)
    public void buildHeap(ArrayList<Node> nodes) {
        minHeap = nodes;           //first loop heap with input nodes
        for (int i = 0; i < minHeap.size(); i++) {  //initialize position according to their name? P0<-N0, P1<-N1....
            position.add(i);
        }
        int j = minHeap.size() / 2;
        while (j >= 0) {   //run heapify_down from n/2 to 0.
            Heapify_down(j);
            j--;
        }
    }

    // insertNode
    //
    // Insert a Node into the heap
    //
    // Time Complexity Requirement: theta(log(n))
    public void insertNode(Node in) {
        minHeap.add(in);   //add node in to the last position of heap
        position.add(in.getNodeName());
        int lastNode = minHeap.size() - 1;
        Heapify_Up(lastNode); //call heapify_up
    }

    // findMin
    //
    // Returns the minimum element of the heap
    //
    // Time Complexity Requirement: theta(1)
    public Node findMin() {
        return minHeap.get(0);
    }

    // extractMin
    //
    // Removes and returns the minimum element of the heap
    //
    // Time Complexity Requirement: theta(log(n))
    public Node extractMin() {
        position.set(0, position.get(minHeap.size() - 1));
        position.remove(minHeap.size() - 1);
        Node minNode = minHeap.get(0);
        minHeap.set(0, minHeap.get(minHeap.size() - 1)); //O(1) set first position of heap to the last node, and remove last node
        minHeap.remove(minHeap.size() - 1);//o(1)
        Heapify_down(0); //call heapify down o(log(n))
        return minNode;
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getNodeName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<Node> toArrayList() {
        return minHeap;
    }
}
