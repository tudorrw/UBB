#!/bin/bash
grep -l "$1" $(find -type f -name "*.txt")

