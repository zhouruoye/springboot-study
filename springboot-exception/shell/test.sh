#!/usr/bin/env bash

let "a=5"
let "b=4"
let "c=a+b"
printf "c = $c \n"

#read msg
#echo "$msg"

array=("value1" "value2" "value3")
array[5]="value6"
#输出下标为2的值
echo ${array[2]}
#输出所有值
echo ${array[*]}

#if条件判断
name1="小明"
name2="小红"

if [ $name1 = "小明" ]
then
    echo "我是小明"
else
    echo "我是小红"
fi

if [ $1 = "111" ]
then
    echo "我是小明111"
else
    echo "我是小红111"
fi