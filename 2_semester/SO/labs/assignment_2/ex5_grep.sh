#!/bin/bash

if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters!"
    exit 1
fi

folder=$1

if [[ ! -d $folder ]]; then
    echo "error: can't open the folder ${folder}"
    exit 1
fi

for file in "$folder"/*; do
    extension="${file##*.}"
    if [[ -f $file && $extension == "c" && $(grep -c "^#include <.*>$" $file) -ge 3 ]]; then
        echo "${file}" 
    fi
done 