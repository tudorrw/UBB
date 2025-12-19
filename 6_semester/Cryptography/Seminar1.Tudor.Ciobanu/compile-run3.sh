#!/bin/bash
javac Assignment3.java
if [ $? -eq 0 ]; then
    java Assignment3
else
    echo "Compilation failed."
fi
