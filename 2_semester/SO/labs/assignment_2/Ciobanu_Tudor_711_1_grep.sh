#!/bin/bash

if [ $# -eq 0 ]; then
    echo "Es gibt kein Parameter!"
    exit 1
fi

#for file in $(find $1 -type f -name "*.txt")
#do
#    grep "" $file
#done

#Unterdrückt es das Präfixieren von Dateinamen bei der Ausgabe (-h = --no-filename)
grep --no-filename "" $(find $1 -type f -name "*.txt")







