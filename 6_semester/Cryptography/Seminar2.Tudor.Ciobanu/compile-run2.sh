#!/bin/bash
javac Assignment2.java
if [ $? -eq 0 ]; then
    java Assignment2
else
    echo "Compilation failed."
fi
