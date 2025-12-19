#!/bin/bash
javac RSA_Hack.java
if [ $? -eq 0 ]; then
    java RSA_Hack
else
    echo "Compilation failed."
fi
