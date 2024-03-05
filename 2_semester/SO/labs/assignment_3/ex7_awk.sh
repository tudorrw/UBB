#!/bin/bash

if [ $# -eq 0 ]; then
    echo "error: give at least one parameter"
    exit 1
fi

for file in $*; do
    if [[ -f $file ]]; then
        awk ' 
        {
            characters=0;
            for(i=1;i<=NF;i++)
                characters = characters + length($i)
            if(characters > 30)
                print NR, $1, $NF;
        }' "$file"

        awk '{if(length($0) > 30) print NR, $1, $NF}' "$file"
    else 
        echo "error: cannot open file $file"
    fi
done