#!/usr/bin/env python

"""

generate_sequence.py

Generates a sequence that can be
read into a list

"""

__author__ = "Bobby Streit"
__version__ = "1.0"
__maintainer__ = "Bobby Streit"
__email__ = "rpstreit@utexas.edu"

import argparse
import random

MAX = 100

PARSER = argparse.ArgumentParser()
PARSER.add_argument("path", help="Output file path")
PARSER.add_argument("length", help="Length of sequence",
                    type=int)
ARGS = PARSER.parse_args()

def main():
    f = open(ARGS.path, "w")
    for i in range(ARGS.length):
        f.write(str(random.randint(1, MAX)) + " ")
    f.close()

if __name__ == "__main__":
    main()
