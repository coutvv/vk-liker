#!/bin/bash
FILE="build/libs/vk-liker.jar"

if [ ! -f $FILE]; then
	echo "Oops! Executing  $FILE not found!"
else 
	exec java -jar -Dfile.encoding=UTF-8 $FILE
fi
