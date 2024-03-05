#!/bin/bash

if [ $# -ne 1 ]; then
    echo "error: invalid number of parameters"
    exit 1
fi
file=$1
if [[ -f $file ]]; then
    awk '
    BEGIN { total_sum = 0; valid_lines = 0; invalid_lines = 0;}
    {
        sum = 0;
        valid = 0;
        for(i = 1; i <= NF; i++) {
            if($i ~ /^[0-9]$/){
                sum += $i;
                valid = 1;
            }
        }
        if (valid) {
            total_sum += sum;
            valid_lines++;
        } else {
            invalid_lines++;
        }
    }
    END { printf("Summe = %d\n", total_sum); printf("Verhältnis enthält_Zahl/enthält_keine_Zahl = %d:%d\n", valid_lines, invalid_lines); }
    ' "$file"
else 
    echo "error: cannot open file $file"
fi