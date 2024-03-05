#!/bin/bash
if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters!"
    exit 1
fi

filename="stoc.csv"
query=$1

grep "^[^,]*,[^,]*, [1-9][0-9]*$" $filename | grep -i "$query"
