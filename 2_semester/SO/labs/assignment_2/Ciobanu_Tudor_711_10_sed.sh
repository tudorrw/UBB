#!/bin/bash

if [ $# -eq 0 ]; then
    echo "es gikt kein Parameter!"
    exit 1
fi

sed -E 's/([a-zA-Z]*)([^a-zA-Z]*)([a-zA-Z]*)([^a-zA-Z]*)([a-zA-Z]*)/\5\2\3\4\1/' $1

#sed -E '0,/([a-zA-Z]*)([^a-zA-Z]*)([a-zA-Z]*)([^a-zA-Z]*)([a-zA-Z]*)/s//\5\2\3\4\1/' $1
