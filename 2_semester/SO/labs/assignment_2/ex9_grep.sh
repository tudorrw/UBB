#!/bin/bash

if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters!"
    exit 1
fi
client_file="$1.csv"
if [[ ! -f $client_file ]]; then    
    echo "the client doesn't exist in our financing company"
    exit 1
fi


# grep "^OUT,[^,]*,[0-9]*$" $client_file 
numbers=$(grep "^OUT" $client_file | grep -oE '[0-9]+')

sum=0
for i in $numbers; do
    sum=$(($sum + $i))
done

echo "sum: $sum"