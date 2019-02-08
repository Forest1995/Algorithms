
import java.io.File;
import java.util.*;

import program2.*;

public class GraphTools
{
  public static UniversalNode MakeUniversal(Node n)
  {
    return new UniversalNode(n.getNodeName(), n.getMinDistance());
  }
  public static ArrayList<UniversalNode> MakeUniversal(ArrayList<Node> g)
  {
    ArrayList<UniversalNode> result = new ArrayList<>();
    for (Node n : g)
    {
      result.add(new UniversalNode(n.getNodeName(), n.getMinDistance()));
    }
    for (int j = 0; j < result.size(); ++j)
    {
      for (int i = 0; i < g.get(j).getNeighbors().size(); ++i)
      {
        int k;
        for (k = 0; k < result.size(); ++k)
        {
          if (result.get(k).getNodeName() == g.get(j).getNeighbors().get(i).getNodeName())
          {
            break;
          }
        }
        if (k < result.size()) result.get(j).setNeighborAndWeight(result.get(k), g.get(j).getWeights().get(i));
      }
    }
    return result;
  }
	public static Graph buildGraph(String filename) 
	{
		int numV = 0, numE = 0;
    Graph result = null;
    Scanner sc;
    try
    {
      sc = new Scanner(new File(filename));	
		  String[] inputSize = sc.nextLine().split(" ");
      numV = Integer.parseInt(inputSize[0]);
      numE = Integer.parseInt(inputSize[1]);
      ArrayList<Node> vertices = new ArrayList<>();
      HashMap<Integer, ArrayList<NeighborWeightTuple>> tempNeighbors = new HashMap<>();
      result = new Graph(numV);
      
      for (int i = 0; i < numV; ++i) 
      {
        String[] pairs = sc.nextLine().split(" ");
        String[] weightPairs = sc.nextLine().split(" ");
        
        Integer currNode = Integer.parseInt(pairs[0]);
        Node currentNode = new Node(currNode);
        vertices.add(currNode, currentNode);
        ArrayList<NeighborWeightTuple> currNeighbors = new ArrayList<>();
        tempNeighbors.put(currNode, currNeighbors);
        
        for(int k = 1; k < pairs.length; k++) {
          Integer neighborVal = Integer.parseInt(pairs[k]);
          Integer weightVal = Integer.parseInt(weightPairs[k]);
          currNeighbors.add(new NeighborWeightTuple(neighborVal, weightVal));
        }
      }
      for (int i = 0; i < vertices.size(); ++i)
      {
        Node currNode = vertices.get(i);
        ArrayList<NeighborWeightTuple> neighbors = tempNeighbors.get(i);
        for (NeighborWeightTuple neighbor : neighbors)
        {
          result.setEdge(currNode, vertices.get(neighbor.neighborID), neighbor.weight);
        }
      }
      
      result.setAllNodesArray(vertices);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.err.println("The script broke");
      System.exit(1);
    }

    return result;
	}
	
  private static class NeighborWeightTuple {
		public Integer neighborID;
		public Integer weight;

		NeighborWeightTuple(Integer neighborID, Integer weight)
		{
			this.neighborID = neighborID;
			this.weight = weight;
		}
	}

}
