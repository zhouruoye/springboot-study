#!/usr/bin/env bash

#while [ -z$response ]||[ $response!='pig' ]
#do
#    read -p 'say pig:' response
#done

while [ -z $response ] || [ $response != 'yes' ]
do
    read -p 'Say yes : ' response
done