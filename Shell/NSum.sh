#!bin/bash

echo "Enter how many numbers you want: "
read n

result=0

for (( i=0; i<n; i++ )); do
    read m
    result=$((result + m))

done

echo "Sum = $result"