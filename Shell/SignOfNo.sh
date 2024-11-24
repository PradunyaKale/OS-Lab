#!bin/bash

echo "Enter the number: "
read n

if [[ $n -lt 0 ]]; then 
    echo "$n is the negative number"
else
    echo "$n is the positive number"
fi