#!/bin/bash
echo -n "Give a number: "
read num


if [ $num -lt 2 ]; then
        echo "not prime"
	exit 1
elif [ $num -gt 1 ]; then
	 n=$(($num/2))
	 for (( i=2; $i<=$n; i++ ))
	 do
		 if [ $(($num%$i)) -eq 0 ]; then
			   echo "not prime"
			   exit 1
		 fi
	 done
 	 echo "prime"
fi




