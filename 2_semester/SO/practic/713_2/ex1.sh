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

rows=0
for file in $(find $directory -type f); do
    file_rows=$(awk 'END{print NR}' "$file")
    # echo "$file_rows"
    # cat $file
    rows=$(($rows+$file_rows))
done

echo "Gesamtanzahl der Zeilen: $rows"
