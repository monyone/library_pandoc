#! /usr/bin/env bash

source_offset='../source/src/main/java/jp/monyone/library/'

verify_dir='../verify'
program_offset='src/main/java/jp/monyone/verify/'
program_dir="${verify_dir}/${program_offset}"
sed_dir='..\/verify\/src\/main\/java\/jp\/monyone\/verify\/'

for program in `find ${program_dir} -type f`; do
  locate_name=`echo ${program} | sed "s/.template//g"`

  #echo ${program} ${locate_name}

  ./paste-verify.py ${source_offset} ${program} ${locate_name}
done



