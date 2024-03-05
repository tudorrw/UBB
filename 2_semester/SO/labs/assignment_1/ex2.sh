#!/bin/bash
if [ $# -ne 1 ]; then
    echo "Invalid number of Parameters!"
    exit 1
fi

folder="$1"


if [ ! -d "$folder" ]; then
    echo "Error: '$folder' is not a valid folder"
    exit 1
fi

count=0

for file in "$folder"/*; do
    if [[ -f "$file" && "$file" == *.txt ]]; then
        text_lines=$(wc -l < "$file")
        count=$((count + text_lines))
    fi
done 
echo "$count"