#! /usr/bin/env python3

import argparse
import re
import os

parser = argparse.ArgumentParser(description='Paste library source code to markdown.')
parser.add_argument('dir', metavar='sourcr-base-dir', type=str, help='base directory for paste code')
parser.add_argument('template', metavar='source-file', type=argparse.FileType('r'), help='source file to copy')
parser.add_argument('dest', metavar='dest-file', type=argparse.FileType('w'), help='destination file to copy')

args = parser.parse_args()

# Arguments
template_file = args.template;
dest_file = args.dest;

# function
def write_paste_code(indent, paste_path, dest_file):
  #print(paste_path)
  
  with open(paste_path, 'r') as paste_file:
    write_flg = False
    for paste_line in paste_file:
      #if 'import static' in paste_line:
         # TODO: NESTED LIBRARY CODE EXPAND
         # NEED: memoized expanding filename in function
      
      if '//@end' in paste_line:
        write_flg = False
      
      if write_flg is True:
        dest_file.write(indent + paste_line)
      
      if '//@start' in paste_line:
        write_flg = True

# 
for template_line in template_file:
  if '//@paste' in template_line:
    #print(template_line)
    template_arg = re.match(r'^\s//[\s]*@paste[\s]*(.[\S]*)', template_line).group(1).replace('.', '/')
    paste_path = args.dir + template_arg + "_Include.java"
    #print(paste_path)
    
    write_paste_code('', paste_path, dest_file)

  else:
    dest_file.write(template_line)

