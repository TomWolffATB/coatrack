#!/bin/sh
#TODO in execute.sh the shebang is missing, which might be why it is not working yet. Dont forget to make script executable first via 'chmod +x'.

NETWORK_NAME="Hello World"
IMAGE_NAME="java-appv2"
CONTAINER_NAME="java-appv2"

#Create image if necessary
#TODO If Ubuntu image with installed software does not exist, then create one.
#TODO Prepare Ubuntu Image
#DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget openjdk-11-jdk maven
#copy file to container
#mvn compile
#docker commit java-app java-app

printf "\nSetting up network and containers"
docker network create "$NETWORK_NAME"
docker run --rm -d --network="$NETWORK_NAME" --shm-size="2g" --name selenium selenium/standalone-firefox
docker run --rm -d --network="$NETWORK_NAME" --name "$CONTAINER_NAME" --entrypoint "/home/execute-tests.sh" "$IMAGE_NAME"

printf "\nCopying project files to java test application container"
docker cp config.properties "${CONTAINER_NAME}:/home"
docker cp src "${CONTAINER_NAME}:/home"
docker cp pom.xml "${CONTAINER_NAME}:/home"

printf "\nFollowing the test execution"
docker logs -f "$CONTAINER_NAME"

printf "\nCleanup"
docker stop selenium
docker network rm "$NETWORK_NAME"