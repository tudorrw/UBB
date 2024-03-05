#!/bin/bash
if [ $# -ne 2 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi
file1=$1
if [[ -f $file1 ]]; then
    awk '{ print }' "$file1"
else 
    echo "error: cannot open file $file1"
fi

file2=$2
if [[ -f $file2 ]]; then
    awk '{for(i=NF; i>1; i--) printf("%s:",$i); print $1}' "$file2"
else 
    echo "error: cannot open file $file2"
fi