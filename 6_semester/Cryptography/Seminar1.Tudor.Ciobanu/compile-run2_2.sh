#!/bin/bash
javac Assignment2_2.java
if [ $? -eq 0 ]; then
    java Assignment2_2
else
    echo "Compilation failed."
fi
