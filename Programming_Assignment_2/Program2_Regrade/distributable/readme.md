Requirements for running:
java 1.8
junit 4
linux (might work on mac, for sure won't work on windows)
python 2.7 or 3.7
a good attitude!

This was tested on the ece lrc machines, and is probably what you will
have to run on. Recall that you log in via ssh, and transfer files with
scp. On windows PuTTY and WinSCP are great clients for these two operations.
On Mac and Linux these operations are native tools that you can just
use by the command line. Google is your friend here.
Here is the ECE LRC page for you to retrieve the password to that account
you forgot about:
http://www.ece.utexas.edu/it/virtual-linux-resources

Included are three programs for you to test with. 

(1) program2_test.py: Used to test your assignment functionality. Similar
in idea to how I originally graded you but heavily modified.

usage: program2_test.py [-h] [-s SEQUENCE] [-r ROOT] [-d DESTINATION]
                        test input

positional arguments:
  test                  Test to run from:
                          build_heap: Requires <input> sequence to build heap from
                          insert: Requires initial heap values defined by <input> and sequence to add defined by <--sequence>
                          find_min: Requires initial heap values defined by <input>
                          extract_min: Requires initial heap values defined by <input>

                          shortest_path_length: Requires graph defined by <input> and <--root> and <--destination> node ids
                          a_shortest_path: Requires graph defined by <input> and <--root> and <--destination> node ids
                          every_shortest_path: Requires graph defined by <input> and <--root> node id
  input                 Test input file path

optional arguments:
  -h, --help            show this help message and exit
  -s SEQUENCE, --sequence SEQUENCE
                        Sequence for heap test (like a sequence of numbers to insert in order)
  -r ROOT, --root ROOT  Root node for a shortest path test
  -d DESTINATION, --destination DESTINATION

Example: To validate your shortest path method from node 7 to node 100 on the large graph you would do 
./program2_test.py a_shortest_path tests/large_graph.txt --root 7 --destination 100

(2) generate_graph.py: A tool to generate random graph files

usage: generate_graph.py [-h] [-d DENSE] [-s SPARSITY] [-dc DISCONNECTED]
                         path numvertices

positional arguments:
  path                  Output file path
  numvertices           Number of Vertices

optional arguments:
  -h, --help            show this help message and exit
  -d DENSE, --dense DENSE
                        Create a dense graph
  -s SPARSITY, --sparsity SPARSITY
                        Percent chance of an edge between two nodes occuring
                        (50 by default)
  -dc DISCONNECTED, --disconnected DISCONNECTED
                        Create a disconnected graph

(3) generate_sequence.py: A tool to generate sequence files that are used
by the heap tests

usage: generate_sequence.py [-h] path length

positional arguments:
  path        Output file path
  length      Length of sequence

optional arguments:
  -h, --help  show this help message and exit


The testing itself is spread across junit classes (that you can modify if 
you desire) and a precompiled library with the verification routines. The
entry point is TestRunner.java. The test runner launches single unit tests
via command line arguments directing it to a junit class and test to run,
and by runtime defined environment variables that feed into the junit 
cases as inputs. To run this directly without my included program, you
will have to compile and run with junit4.jar and the Program2Verifier.jar
on your classpath. If you don't know what I am talking about then don't
bother with this and save yourself some time just by using my program.


