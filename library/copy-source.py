#! /usr/bin/env python3

import argparse
import re

parser = argparse.ArgumentParser(description='Copy from project source code to library.')
parser.add_argument('source', metavar='source-file', type=argparse.FileType('r'), help='source file to copy')
parser.add_argument('dest', metavar='dest-file', type=argparse.FileType('w'), help='destination file to copy')

args = parser.parse_args()

# Arguments
source_file = args.source;
dest_file = args.dest;

# 
remove_flg = True
offset = ''
breakline = False

for source_line in source_file:
  if '//@end' in source_line:
    remove_flg = True
  
  if remove_flg is False:
    dest_file.write(re.sub('^' + offset, '', source_line))
  
  if '//@start' in source_line:
    remove_flg = False
    offset = re.match(r'(^\s*)', source_line).group(1).replace('\n', '')
    if breakline is True:
      dest_file.write('\n')
    else:
      breakline = True
