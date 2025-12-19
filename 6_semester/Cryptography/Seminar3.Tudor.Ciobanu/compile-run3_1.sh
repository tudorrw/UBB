#!/bin/bash
javac DES.java
if [ $? -eq 0 ]; then
    java DES
else
    echo "Compilation failed."
fi
