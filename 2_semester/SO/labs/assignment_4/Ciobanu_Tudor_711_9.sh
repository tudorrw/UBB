#!/bin/bash
if [ "$#" -ne 3 ]; then
    echo "nonono"
    exit 1
fi

ersetzungen=0
for file in $(find $1 -type f -name "*.c")
do
    while read -r line; do
        if [[ $line == *"$2("* && ! $(echo $line | grep "int $2\|float $2\|double $2\|void $2\|char $2") ]]; then
            echo $line
            new_line=$(echo $line | sed "s/\(.*(\)\([^)]*\)\().*\)/\1$3\3/g")
            echo $new_line
            sed -i "s/$line/$new_line/g" "$file"
            ersetzungen=$((ersetzungen + 1))
        fi
    done < $file
done 

echo "Ersetzungen: $ersetzungen"

# \(.*(\) matches any characters before an open parenthesis
# \([^)]*\) matches any characters that are not a closing parenthesis
# \().*\) the closing parenthesis 
