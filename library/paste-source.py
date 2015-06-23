#! /usr/bin/env python3

import argparse
import re
import os

parser = argparse.ArgumentParser(description='Paste library source code to markdown.')
parser.add_argument('source', metavar='source-file', type=argparse.FileType('r'), help='source file to copy')
parser.add_argument('dest', metavar='dest-file', type=argparse.FileType('w'), help='destination file to copy')

args = parser.parse_args()

# Arguments
source_file = args.source;
source_dir = os.path.dirname(source_file.name)
dest_file = args.dest;


# 
for source_line in source_file:
  if '~~~~{' in source_line:
    code_name = re.match(r'~~~~\{(.*)\}', source_line).group(1)
    dest_file.write('~~~~{.java}\n')
    if code_name != '.java':
      with open(source_dir + '/' + code_name, 'r') as code_file:
        for code_line in code_file:
          dest_file.write(code_line)
  else:
    dest_file.write(source_line)


