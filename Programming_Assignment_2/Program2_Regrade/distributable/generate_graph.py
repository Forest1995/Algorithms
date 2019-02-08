#!/usr/bin/env python

""" 

generate_graph.py

Generates a random graph for grading. For use
please run the module with --help

"""

__author__ = "Bobby Streit"
__version__ = "1.0"
__maintainer__ = "Bobby Streit"
__email__ = "rpstreit@utexas.edu"

import argparse
import random

MAX_WEIGHT = 100

PARSER = argparse.ArgumentParser()
PARSER.add_argument("path", help="Output file path")
PARSER.add_argument("numvertices", help="Number of Vertices",
                    type=int)
PARSER.add_argument("-d", "--dense", help="Create a dense graph")
PARSER.add_argument("-s", "--sparsity",
                    help="Percent chance of an edge between two nodes occuring (50 by default)",
                    type=int)
PARSER.add_argument("-dc", "--disconnected", help="Create a disconnected graph")
ARGS = PARSER.parse_args()

if ARGS.sparsity:
    SPARSITY = ARGS.sparsity
else:
    SPARSITY = 50

def main():
    vertices = []
    edgecount = 0
    for i in range(ARGS.numvertices):
        vertices.append([0] * ARGS.numvertices)
    if not ARGS.disconnected:
        for i in range(ARGS.numvertices):
            for j in range(i + 1, ARGS.numvertices):
                if ARGS.dense or random.randint(0, 99) < SPARSITY:
                    weight = random.randint(1, MAX_WEIGHT)
                    vertices[i][j] = weight
                    vertices[j][i] = weight
                    edgecount += 1
    f = open(ARGS.path, "w")
    f.write(str(ARGS.numvertices) + " " + str(edgecount))
    for i in range(ARGS.numvertices):
        f.write("\n" + str(i))
        for j in range(ARGS.numvertices):
            if vertices[i][j] != 0:
                f.write(" " + str(j))
        f.write("\n" + str(i))
        for j in range(ARGS.numvertices):
            if vertices[i][j] != 0:
                f.write(" " + str(vertices[i][j]))

if __name__ == "__main__":
    main()
