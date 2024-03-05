#!/bin/sh
for file in $*
do    
    awk 'BEGIN{FS=""}
    {for(i=1;i<=NF; i++){
        if($i ~ /[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]+/)
            c++
    }
    } END {print FILENAME, v, c}' $file


done


awk 'BEGIN{FS=" "}
{
  c = 0;
  for(i=1; i<=NF; i++) {
    word = $i;
    if([^bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]", "", word);
    if (length(word) >= 4 && length(word) <= 6) {
      line_consonants++;
    }
  }
  if (line_consonants > 0) {
    print $0;
  }
}' $filename