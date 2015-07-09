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

# merge string
def merge_string(str1, str2):
  for i in range(min(len(str1), len(str2)), 0, -1):
    #print(str1[-i:], str2[:i])
    if str1[-i:] == str2[:i]:
      return str1[:-i] + str2[:i] + str2[i:]
  return str1 + str2

# PreScan Result
import_lines = set()

# recursive collecting "import ~~~"
def recursive_import_collecting(source_path, visit_paths):
  if source_path in visit_paths:
    return
  
  with open(source_path, 'r') as source_file:
    visit_paths.add(source_path)
    
    for source_line in source_file:
      import_static_matched = re.match(r'^[\s]*import[\s]+static[\s]*([\S]*)', source_line)
      
      if import_static_matched is not None:
        import_static_path = import_static_matched.group(1).replace('.', '/')
        import_static_merged = merge_string(args.dir, import_static_path);
        import_static_file_path = re.sub(r'/[^/]*$', '', import_static_merged) + '.java'
        #print(import_static_merged)
        #print(import_static_file_path)
        recursive_import_collecting(import_static_file_path, visit_paths);
      elif 'import' in source_line:
        import_lines.add(source_line.replace('\n', ''));
    
    visit_paths.remove(source_path)

def collect_nested_import(path):
  with open(path, 'r') as file:
    for line in file:
      matched = re.match(r'^[\s]*//[\s]*@paste[\s]*(.[\S]*)', line)
      if matched is not None:
        paste_arg = matched.group(1).replace('.', '/')
        paste_path = args.dir + paste_arg + "_Include.java"
        recursive_import_collecting(paste_path, set())

collect_nested_import(template_file.name)
#print(import_lines)
  
# function
paste_file_paths = set()
def write_paste_code(indent, paste_path, dest_file):
  if paste_path in paste_file_paths:
    return
  paste_file_paths.add(paste_path)
  #print(paste_path)
  
  with open(paste_path, 'r') as paste_file:
    write_flg = False
    for paste_line in paste_file:
      import_static_matched = re.match(r'^[\s]*import[\s]+static[\s]*([\S]*)', paste_line)
      if import_static_matched is not None: 
        import_static_path = import_static_matched.group(1).replace('.', '/')
        import_static_merged = merge_string(args.dir, import_static_path);
        import_static_file_path = re.sub(r'/[^/]*$', '', import_static_merged) + '.java'
        if not import_static_file_path in paste_file_paths:
          write_paste_code(indent, import_static_file_path, dest_file)
          dest_file.write('\n');

      start_matched = re.match(r'^[\s]*//[\s]*@start[\s]*', paste_line)
      end_matched = re.match(r'^[\s]*//[\s]*@end[\s]*', paste_line)
      if end_matched is not None:
        write_flg = False
      
      if write_flg is True:
        dest_file.write(indent + paste_line)
      
      if start_matched is not None:
        write_flg = True

# main code 
first_line = True
just_below_package = False
for template_line in template_file:
  if import_lines:
    if first_line is True:
      first_line = False
      if 'package' not in template_line:
        for import_line in import_lines:
          dest_file.write(import_line + '\n')
      else:
        just_below_package = True
  
    elif just_below_package is True:
      just_below_package = False
      dest_file.write('\n')
      for import_line in import_lines:
        dest_file.write(import_line + '\n')
  
  template_matched = re.match(r'^[\s]*//[\s]*@paste[\s]*(.[\S]*)', template_line)
  if template_matched is not None:
    #print(template_line)
    template_arg = template_matched.group(1).replace('.', '/')
    paste_path = args.dir + template_arg + "_Include.java"
    #print(paste_path)
    
    write_paste_code('', paste_path, dest_file)

  else:
    dest_file.write(template_line)

