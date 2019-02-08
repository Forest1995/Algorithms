import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import program2.*;

public class ShortestPathTests
{

  Graph graph;
  GraphVerifier verifier;
  String input_path;
  int root_id;
  int dest_id;

  @Before
  public void setup()
  {
  	input_path = System.getProperty("INPUT_PATH");
    root_id = Integer.parseInt(System.getProperty("ROOT", "0"));
    dest_id = Integer.parseInt(System.getProperty("DEST", "0"));
//    System.out.println("input_path: " + input_path);
//    System.out.println("root: " + root_id);
//    System.out.println("dest: " + dest_id);
    graph = GraphTools.buildGraph(input_path);
    verifier = new GraphVerifier();
    verifier.buildVerifier(input_path);
  }

  @Test
  public void findShortestPathLength_test()
  {
//      System.out.println(""+root_id+"->"+dest_id);
    Node root = graph.getAllNodes().get(root_id);
    Node dest = graph.getAllNodes().get(dest_id);

    int student_result = graph.findShortestPathLength(root, dest);
    Assert.assertTrue(verifier.verifyShortestPathLength(student_result, GraphTools.MakeUniversal(root), GraphTools.MakeUniversal(dest)));

  }

  @Test
  public void findAShortestPath_test()
  {
    Node root = graph.getAllNodes().get(root_id);
    Node dest = graph.getAllNodes().get(dest_id);
    
    ArrayList<UniversalNode> g = GraphTools.MakeUniversal(graph.getAllNodes());
    ArrayList<UniversalNode> path = new ArrayList<>();
    
    ArrayList<Node> spath = graph.findAShortestPath(root,  dest);
    for (Node n : spath)
    {
    	for (UniversalNode u : g)
    	{
    		if (n.getNodeName() == u.getNodeName())
    		{
    			path.add(u);
    		}
    	}
    }
    
    Assert.assertTrue(verifier.verifyPath(GraphTools.MakeUniversal(root),
    		GraphTools.MakeUniversal(dest),
    		path));
  }

  @Test
  public void findEveryShortestPath_test()
  {
    Node root = graph.getAllNodes().get(root_id);
    Node dest = graph.getAllNodes().get(dest_id);

    Assert.assertTrue(verifier.verifyEveryMinDistance(GraphTools.MakeUniversal(root), GraphTools.MakeUniversal(graph.findEveryShortestPathLength(root))));
  }
}
