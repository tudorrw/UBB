#!/bin/bash

if [ $# -eq 0 ]; then
    echo "error: give at least one parameter"
    exit 1
fi

for file in $*; do
    if [[ -f $file ]]; then
        awk '{ a[i++]=$0} END {for(j=i-1; j>=0; j--) print a[j] }' "$file"
    else
        echo "cannot open file ${file}"
    fi
done