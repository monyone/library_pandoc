#! /usr/bin/env bash

project_dir='../source'
program_offset='src/main/java/jp/monyone/library/'
program_dir="${project_dir}/${program_offset}"
sed_dir='..\/source\/src\/main\/java\/jp\/monyone\/library\/'

for program in `find ${program_dir} -type f`; do
  locate_name=`echo ${program} | sed "s/${sed_dir}//g" | sed "s/_Include//g"`

  #echo ${program} ${locate_name}

  ./copy-source.py ${program} ${locate_name}
done



