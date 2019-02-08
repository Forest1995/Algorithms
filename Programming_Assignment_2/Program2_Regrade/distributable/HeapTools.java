
import java.io.File;
import java.util.*;

import program2.*;

public class HeapTools
{
	static int cnt = 0;
  static public ArrayList<Integer> Nodes2Int(ArrayList<Node> list)
  {
    ArrayList<Integer> int_list = new ArrayList<>();
    for (Node n : list)
    {
      int_list.add(n.getMinDistance());
    }

    return int_list;
  }
	static public ArrayList<Node> listFromFile(String filename, int idOffset)
	{

		ArrayList<Node> vertices = null;
    try
    {
      Scanner sc = new Scanner(new File(filename));	
		  String[] distances = sc.nextLine().split(" ");
      vertices = new ArrayList<>();
      

      for (String s : distances) 
      {
        Integer d = Integer.parseInt(s);
        Node currentNode = new Node(cnt);
        cnt++;
				currentNode.setMinDistance(d);
        vertices.add(currentNode);
      }
    }
    catch (NoSuchElementException ne) {} 
    catch (Exception e)
    {
      e.printStackTrace();
      System.err.println("The script broke");
      System.exit(1);
    }

		return vertices;
	}
}
