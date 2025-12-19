#!/bin/bash
javac Assignment1.java
if [ $? -eq 0 ]; then
    java Assignment1
else
    echo "Compilation failed."
fi
