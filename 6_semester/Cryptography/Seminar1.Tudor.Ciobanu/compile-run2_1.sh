#!/bin/bash
javac Assignment2_1.java
if [ $? -eq 0 ]; then
    java Assignment2_1
else
    echo "Compilation failed."
fi
