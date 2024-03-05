#!/bin/bash

if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi

file="$1"

if [[ ! -f $file ]]; then
    echo "error: cannot open file ${file}"
    exit 1
fi

sed -E '0,/([^ ]+) ([^ ]+) ([^ ]+) ([^ ]+)/s//\1 \3/' $file


