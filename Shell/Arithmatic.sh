#!bin/bash

echo "Enter first number: "
read a
echo "Enter second number: "
read b

echo "Enter the operator: "
read c
case "$c" in 
    "+")
        echo "Addition: $((a+b))";;
    "-")
        echo "Subtraction: $((a-b))";;
    "*")
        echo "Multiplication: $((a*b))";;
    "/")
        echo "Division: $((a/b))";;
esac