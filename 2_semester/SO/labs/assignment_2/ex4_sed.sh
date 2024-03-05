#!/bin/bash

if [ $# -ne 2 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi

file="$1"

if [[ ! -f $file ]]; then
    echo "error: cannot open file ${file}"
    exit 1
fi

word="$2"

# sed -E "s/([^A-Za-z])([a-z]+)/\1${word}\2/g" $file
sed "s/[a-z]/${word}&/g" $file