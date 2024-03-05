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

grep -E '[bcdfgjklmnpqrstvwxyz][aeiou]{2,4}' "$file"
