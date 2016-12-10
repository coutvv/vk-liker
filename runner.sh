if [ -f "target/vk-liker-jar-with-dependencies.jar" ]; then
    exec java -jar target/vk-liker-jar-with-dependencies.jar
else 
	echo "Oops! Executing file not found!"
fi
