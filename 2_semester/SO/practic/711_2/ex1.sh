#!/bin/bash

if [ $# -ne 2 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi

file=$1
if [ ! -f $file ]; then
    echo "error: cannot open file ${file}"
    exit 1
fi

words=$2
count=$(grep -c "\<$words\>" $file)
echo "Das Wort \"$words\" erscheint in $count Zeilen."
