#!/bin/bash

if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi

directory=$1
if [ ! -d $directory ]; then
    echo "error: cannot open directory ${directory}"
    exit 1
fi

files=$(find $directory -type f -name "*.txt" | wc -l)
directories=$(find $directory -type d | wc -l)
echo "Gesamtzahl Dateien: $files"
echo "Gesamntzahl Verzeichnisse: $directories"

