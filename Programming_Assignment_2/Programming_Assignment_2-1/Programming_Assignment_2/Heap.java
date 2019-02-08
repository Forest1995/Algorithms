
import java.util.ArrayList;


public class Heap {

	private ArrayList<Node> minHeap; // do not remove

	public Heap() {
		minHeap = new ArrayList<Node>(); // do not remove
	}
	
	// buildHeap
  //
  // Given an ArrayList of Nodes, build a minimum heap keyed on each
  // Node's minDistance
  //
  // Time Complexity Requirement: theta(n)
	public void buildHeap(ArrayList<Node> nodes) {


  }

  // insertNode
  //
  // Insert a Node into the heap
  //
  // Time Complexity Requirement: theta(log(n))
	public void insertNode(Node in) {
	
	}
	
  // findMin
  //
  // Returns the minimum element of the heap
  //
  // Time Complexity Requirement: theta(1)
	public Node findMin() {

		
		return null;
	}

  // extractMin
  //
  // Removes and returns the minimum element of the heap
  //
  // Time Complexity Requirement: theta(log(n))
	public Node extractMin() {


    return null;
  }
	
  public String toString() {
		String output = "";
		for(int i = 0; i < minHeap.size(); i++) {
			output+= minHeap.get(i).getNodeName() + " ";
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
