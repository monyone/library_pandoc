#! /usr/bin/env bash

section_name=`echo $1 | sed 's/\/$//g'`
section_file="${section_name}/section.tex"

echo "\\section{${section_name}}" > ${section_file}

for file in ${1}*.mdc; do
  tex_file=`echo ${file} | sed 's/.mdc/.tex/g'`
  
  echo "\\input{${tex_file}}" >> ${section_file}
  echo '\newpage' >> ${section_file}
done
