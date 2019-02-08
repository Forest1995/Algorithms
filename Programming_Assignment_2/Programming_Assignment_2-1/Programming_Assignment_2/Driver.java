
import java.io.File;
import java.util.*;
/*
	This Driver file will be replaced by ours during our grading.
*/
public class Driver {
	private static String filename; // input file name 
	private static boolean testDijkstra; // set to true by -d flag
	private static boolean testHeap; // set to true by -w flag
	private static Graph testGraph; // instance of your graph
	private static ArrayList<Node> allNodeNames;
	private static void usage() { // error message
		System.err.println("usage: java Driver [-d] [-h] <filename>");
		System.err.println("\t-d\tTest Dijkstra implementation");
		System.err.println("\t-h\tTest the heap implementation");
		System.exit(1);
	}
	
  public static void main(String[] args) throws Exception{
		allNodeNames = new ArrayList<Node>();
		//filename = "/Users/melaniefeng/eclipse-workspace/Program2/src/3.txt";
		//parseInputFile(filename); 
    parseArgs(args);
    parseInputFile(filename);
    testRun();
	
	}

	public static void parseArgs(String[] args) {
    boolean flagsPresent = false;
		if (args.length == 0) {
      usage();
    }
    
    filename = "";
    
    testHeap= false;
    for (String s : args) {
      if(s.equals("-d")) {
        flagsPresent = true;
        testDijkstra = true;
      } else if(s.equals("-h")) {
        flagsPresent = true;
        testHeap = true;
      } else if(!s.startsWith("-")) {
        filename = s;
      } else {
        System.err.printf("Unknown option: %s\n", s);
        usage();
      }
    }
    
    if(!flagsPresent) {
        testDijkstra = true;
        testHeap = true;
    }
	}
	public static void parseInputFile(String filename) 
			throws Exception {
		int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));	
		String[]  inputSize = sc.nextLine().split(" ");
		numV = Integer.parseInt(inputSize[0]);
		numE = Integer.parseInt(inputSize[1]);
		ArrayList<Integer> tempNames = new ArrayList<Integer>();
		testGraph = new Graph(numV);
		for (int i = 0; i < numV; i++) {
			
			String[] pairs = sc.nextLine().split(" ");
			String[] weightPairs = sc.nextLine().split(" ");
			
			Integer currNode = Integer.parseInt(pairs[0]);
			Node currentNode = new Node(currNode);
			allNodeNames.add(currNode, currentNode);
			tempNames.add(currNode);
			
			for(int k = 1; k < pairs.length; k++) {
				Integer neighborVal = Integer.parseInt(pairs[k]);
				Node neighborK;
				if(!tempNames.contains(neighborVal)) {
					neighborK = new Node(neighborVal);
				}
				else 
					neighborK = allNodeNames.get(neighborVal);
				testGraph.setEdge(currentNode, neighborK, Integer.parseInt(weightPairs[k]));
			}

		}
		
		testGraph.setAllNodesArray(allNodeNames);
	}
	
  public static void testRun() {
    if (testDijkstra) {
        System.out.println(testGraph);
    }

    if (testHeap) {
        System.out.println(testGraph.getHeap());
    }
	}
}
