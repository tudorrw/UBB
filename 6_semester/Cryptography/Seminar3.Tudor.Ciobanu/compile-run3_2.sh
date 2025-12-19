#!/bin/bash
javac AES.java
if [ $? -eq 0 ]; then
    java AES
else
    echo "Compilation failed."
fi
