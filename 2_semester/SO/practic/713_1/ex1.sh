#!/bin/bash

if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi

file=$1
if [ ! -f $file ]; then
    echo "error: cannot open file ${file}"
    exit 1
fi
touch reversed.txt
awk '{a[i++]=$0} END {for(j=i-1;j >= 0; j--) print a[j]}' "$file" > reversed.txt