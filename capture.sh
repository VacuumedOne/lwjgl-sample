gradle shadowJar

JAVA=`which java`
CUR=`pwd`
COMMAND="$JAVA -jar $CUR/build/libs/all.jar"
echo $COMMAND
CAPTURE_NAME=name
renderdoccmd capture -d . -c $CAPTURE_NAME $COMMAND