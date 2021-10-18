#!/bin/sh

NETWORK_NAME="selenium-test-network"
IMAGE_NAME="selenium-test-executor"
CONTAINER_NAME="selenium-test-executor"
SELENIUM_CONTAINER_NAME="selenium"

docker stop $CONTAINER_NAME
docker stop $SELENIUM_CONTAINER_NAME

if docker images | grep -q "selenium/standalone-firefox"; then
  printf "\nA selenium server image is already installed. No pull required.\n"
else
  printf "\nSelenium server image is pulled.\n"
  docker pull selenium/standalone-firefox
fi

if docker images | grep -q $IMAGE_NAME; then
  printf "\nA selenium-test-executor image is already installed. No build required.\n"
else
  printf "\nBuilding a selenium-test-executor image.\n"
  docker build -t $IMAGE_NAME .
fi

printf "\nSetting up network and containers\n"
docker network create "$NETWORK_NAME"
docker run --rm -d --network="$NETWORK_NAME" --shm-size="2g" --name "$SELENIUM_CONTAINER_NAME" selenium/standalone-firefox
docker run --rm -d --network="$NETWORK_NAME" --name "$CONTAINER_NAME" "$IMAGE_NAME"

printf "\nCopying project files to java test application container\n"
docker cp config.properties "${CONTAINER_NAME}:/home"
docker cp src "${CONTAINER_NAME}:/home"
docker cp pom.xml "${CONTAINER_NAME}:/home"

printf "\nFollowing the test execution\n"
docker logs -f "$CONTAINER_NAME"

printf "\nCleanup\n"
docker stop selenium
docker network rm "$NETWORK_NAME"
