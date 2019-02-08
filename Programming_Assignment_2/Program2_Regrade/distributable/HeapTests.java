
import java.util.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import program2.*;

public class HeapTests
{
  Heap heap = new Heap();
  HeapVerifier verifier = new HeapVerifier();
  ArrayList<Node> input;
  ArrayList<Node> sequence;
  String input_path;
  String sequence_path;

  @Before
  public void setup()
  {  
  	input_path = System.getProperty("INPUT_PATH");
  	sequence_path = System.getProperty("SEQUENCE_PATH");
    input = HeapTools.listFromFile(input_path, 0);
    sequence = HeapTools.listFromFile(sequence_path, 1000);
    verifier.buildHeapVerifier(HeapTools.Nodes2Int(input));
  }

  @Test
  public void buildHeap_test()
  {
    // builds a heap from input, and verifies that the
    // heap property is valid
    heap.buildHeap(input);
    Assert.assertTrue(verifier.verifyHeap(HeapTools.Nodes2Int(heap.toArrayList())));
  }

  @Test
  public void insertNode_test()
  {
    // inserts nodes in order specified by sequence, and
    // checks that heap is still correct after each
    try
    {
      heap.buildHeap(input);
      
      for (Node n : sequence)
      {
        heap.insertNode(n);
        Assert.assertTrue(verifier.verifyHeap(HeapTools.Nodes2Int(heap.toArrayList())));
        verifier.insert(n.getMinDistance());
        Assert.assertTrue(verifier.verifyElements(HeapTools.Nodes2Int(heap.toArrayList())));
      }
    }
    catch (Exception e) 
    { 
      e.printStackTrace();
      System.exit(1);
    } 
  }

  @Test
  public void findMin_test()
  {
    // compares their min to verifier min
    try
    {
      heap.buildHeap(input);
      Assert.assertTrue(verifier.findMin() == heap.findMin().getMinDistance());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Test
  public void extractMin_test()
  {
    // extract min until empty, continually checking to make 
    // sure that both the extracted value is greater or eq to the
    // previous and that the heap is correct
    try
    {
      heap.buildHeap(input);
      int lastMin = Integer.MIN_VALUE;
      for (Node n : input)
      {
        Node min = heap.extractMin();
        Assert.assertTrue(min.getMinDistance() >= lastMin);
        lastMin = min.getMinDistance();
        Assert.assertTrue(verifier.verifyHeap(HeapTools.Nodes2Int(heap.toArrayList())));
        verifier.extractMin();
        Assert.assertTrue(verifier.verifyElements(HeapTools.Nodes2Int(heap.toArrayList())));
      }	      
    }
    catch (Exception e) 
    { 
      e.printStackTrace();
      System.exit(1);
    } 
  }
}
