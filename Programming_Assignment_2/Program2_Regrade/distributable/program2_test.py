#!/usr/bin/env python

from __future__ import print_function

""" 

program2_test.py

Program for students to test lab 2. For use
please run the module with --help

"""

__author__ = "Bobby Streit"
__version__ = "1.0"
__maintainer__ = "Bobby Streit"
__email__ = "rpstreit@utexas.edu"

import argparse
from argparse import RawTextHelpFormatter
import subprocess
import sys

tests_help = "\n  build_heap: Requires <input> sequence to build heap from\n  Requires initial heap values defined by <input> and sequence to add defined by <--sequence>\n  Requires initial heap values defined by <input>\n  extract_min: Requires initial heap values defined by <input>\n\n  shortest_path_length: Requires graph defined by <input> and <--root> and <--destination> node ids\n  a_shortest_path: Requires graph defined by <input> and <--root> and <--destination> node ids\n  every_shortest_path: Requires graph defined by <input> and <--root> node id"

PARSER = argparse.ArgumentParser(formatter_class=RawTextHelpFormatter)
PARSER.add_argument("test", help="Test to run from:" + tests_help)
PARSER.add_argument("input", help="Test input file path")
PARSER.add_argument("-s", "--sequence", help="Sequence for heap test (like a sequence of numbers to insert in order)")
PARSER.add_argument("-r", "--root", help="Root node for a shortest path test")
PARSER.add_argument("-d", "--destination", help="Destination for shortest path between two nodes")
ARGS = PARSER.parse_args()
p = subprocess.Popen(["find /usr/share/java -name \"junit4.jar\""], shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
JUNIT, err = p.communicate()
err = err.decode("utf-8")
JUNIT = JUNIT.decode("utf-8").rstrip()
print(err)
#print("junit path: " + JUNIT)
p = subprocess.Popen(["find . -name \"Program2Verifier.jar\""], shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
VER, err = p.communicate()
VER = VER.decode("utf-8").rstrip()
#print("test jar: " + VER)
SEPERATOR = ":"
cp = JUNIT+SEPERATOR+VER+SEPERATOR+"."
print("class path: " + cp)

def main():
    c = subprocess.Popen(["javac -cp " + cp + " *.java"], shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = c.communicate()
    print(out.decode("utf-8"))
    print(err.decode("utf-8"))
    if c.returncode != 0:
        print("\nuh you got some compiler errors\n")
        sys.exit()
    if ARGS.test == "build_heap":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input + " -DSEQUENCE_PATH=tests/none_seq.txt TestRunner HeapTests#buildHeap_test"
    elif ARGS.test == "insert":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input+ " -DSEQUENCE_PATH=" + ARGS.sequence + " TestRunner HeapTests#insertNode_test"
    elif ARGS.test == "find_min":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input + " -DSEQUENCE_PATH=tests/none_seq.txt TestRunner HeapTests#findMin_test"
    elif ARGS.test == "extract_min":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input+ " -DSEQUENCE_PATH=tests/none_seq.txt TestRunner HeapTests#extractMin_test"
    elif ARGS.test == "shortest_path_length":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input+ " -DROOT=" + ARGS.root + " -DDEST=" + ARGS.destination + " TestRunner ShortestPathTests#findShortestPathLength_test"
    elif ARGS.test == "a_shortest_path":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input + " -DROOT=" + ARGS.root + " -DDEST=" + ARGS.destination + " TestRunner ShortestPathTests#findAShortestPath_test"
    elif ARGS.test == "every_shortest_path_length":
        call = "java -cp " + cp + " -DINPUT_PATH=" + ARGS.input + " -DROOT=" + ARGS.root + " -DDEST=0 TestRunner ShortestPathTests#findEveryShortestPath_test"
    else:
        print("error: nonsupported argument " + ARGS.test + "!")
        exit()

    p = subprocess.Popen([call], shell = True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = p.communicate()
    status = p.returncode
    print(out.decode("utf-8"))
    print(err.decode("utf-8"))
    if status != 0:
        print("\n" + ARGS.test + ": failure\n")
    else:
        print("\n" + ARGS.test + ": pass\n")

if __name__ == "__main__":
    main()
