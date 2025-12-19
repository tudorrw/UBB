#!/bin/bash
javac RSA_Encryption.java
if [ $? -eq 0 ]; then
    java RSA_Encryption
else
    echo "Compilation failed."
fi
