#!/bin/bash
if [ $# -eq 0 ]; then
    echo "error: give at least one parameter"
    exit 1
fi
max_words=0
max_file=""
for file in $*; do
    if [[ -f $file ]]; then
        words=$(awk '
        {for(i=1;i<=NF;i++)
            if($i ~ /^[A-Za-z]+$/)
                words++
        }
        END{print words}' "$file")
        if [ $words -gt $max_words ]; then
            max_words=$words
            max_file=$file
        fi
    else
        echo "cannot open file ${file}"
    fi
done
echo "$max_file $max_words"

# datei.txt noten.txt words.txt