
public class Heap
{

private ArrayList<Node> minHeap; // do not remove

private void swap(int n1, int n2)
{
	Node temp = minHeap.get(n1);
	minHeap.set(n1, minHeap.get(n2));
	minHeap.set(n2, temp);
}

private void heapify_down(int idx)
{
  if (minHeap.size() <= (idx * 2) + 1 && minHeap.size() <= (idx * 2) + 2)
  {
    return;
  }
  else if (minHeap.size() <= (idx * 2) + 2)
  {
    if (minHeap.get(idx).getMinDistance() > minHeap.get(idx*2+1).getMinDistance())
    {
      this.swap(idx, idx*2+1);
      this.heapify_down(idx*2 + 1);
    }
  }
  else
  {
    int val_l = minHeap.get(idx*2+1).getMinDistance();
    int val_r = minHeap.get(idx*2+2).getMinDistance();

    int min_idx = val_l > val_r ? idx*2+2 : idx*2+1;
    if (minHeap.get(idx).getMinDistance() > minHeap.get(min_idx).getMinDistance())
    {
      this.swap(idx, min_idx);
      this.heapify_down(min_idx);
    }
  }
}

private void heapify_up(int idx)
{
  if (idx == 0) return;
  int parent_idx = (idx - 1) / 2;
  if (minHeap.get(idx).getMinDistance() < minHeap.get(parent_idx).getMinDistance())
  {
    this.swap(idx, parent_idx);
    this.heapify_up(parent_idx);
  }
}

public Heap()
{
	minHeap = new ArrayList<Node>(); // do not remove
}
// buildHeapVerifier
//
// Given an ArrayList of Nodes, build a minimum heap keyed on each
// Node's minDistance
//
// Time Complexity Requirement: theta(n)
public void buildHeap(ArrayList<Node> Nodes) {
  int i;
  this.minHeap = new ArrayList<Node>(Nodes); // copy construct
  for (i = Nodes.size() - 1; i >= 0; --i)
  {
    this.heapify_down(i);
  }
}

// insertNode
//
// Insert a Node into the heap
//
// Time Complexity Requirement: theta(log(n))
public void insertNode(Node in) {
	minHeap.add(in);
  heapify_up(minHeap.size() - 1);
}

// findMin
//
// Returns the minimum element of the heap
//
// Time Complexity Requirement: theta(1)
public Node findMin() {
	if(minHeap.isEmpty())
		return null;
	return minHeap.get(0);
}

// extractMin
//
// Removes and returns the minimum element of the heap
//
// Time Complexity Requirement: theta(log(n))
public Node extractMin() {
  Node result = minHeap.get(0);
  minHeap.set(0, minHeap.get(minHeap.size() - 1));
  minHeap.remove(minHeap.size() - 1);
  this.heapify_down(0);

  return result;
}


///////////////////////////////////////////////////////////////////////////////
//                         DANGER ZONE                                     //
//              everything below is used for grading                       //
//                    please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

public ArrayList<Node> toArrayList() {
	return minHeap;
}
}
