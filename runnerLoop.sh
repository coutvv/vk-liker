#!/bin/bash
FILE="target/vk-liker-jar-with-dependencies.jar"

if [ ! -f $FILE]; then
    echo "Oops! Executing  $FILE not found!"
else 
    exec java -jar $FILE loop
fi
