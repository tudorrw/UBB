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

sed -i "s/\<${word}\>//g" $file #ex1 removes ever occurrence of a word given as parameter
sed -i "/\<${word}\>/d" $file #ex2 removes the lines that contain a given text and changes the file
sed "1,30 { /\<${text}\>/d }" $file #ex3 removes first 30 lines that contain a given text
sed "s/[a-z]/${word}&/g" $file #ex4 adds a given before every lowercase
sed -E 's/\b([[:alpha:]]+[[:digit:]]+[[:alpha:]]*|[[:alpha:]]*[[:digit:]]+[[:alpha:]]+)\b//g' $file #ex5
sed 'y/aeiou|AEIOU/AEIOU|aeiou/' $file  #ex7 replaces the lowercase vowels with uppercase and vice versa
sed -E 's/^([^:]+):([^:]+):([^:]+)/\1:\2:\1/' $file #ex8
sed -E 's/([a-zA-Z]*)([^a-zA-Z]*)([a-zA-Z]*)([^a-zA-Z]*)([a-zA-Z]*)/\5\2\3\4\1/' $1 #ex10

sed 's/^([^ ]+) ([^ ]+) ([^ ]+) ([^ ]+)/\1 \3/g' $file