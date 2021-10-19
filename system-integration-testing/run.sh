#!/bin/sh

NETWORK="selenium-test-network"
TEST_EXECUTOR="selenium-test-executor"
SELENIUM_SERVER="selenium-server"

#TODO Maybe add 'conditional logic' here so instead of errors proper text message are displayed.
docker stop $TEST_EXECUTOR
docker stop $SELENIUM_SERVER

if docker images | grep -q "selenium/standalone-firefox"; then
  printf "\nA selenium server image is already installed. No pull required.\n"
else
  printf "\nSelenium server image is pulled.\n"
  docker pull selenium/standalone-firefox
fi

if docker images | grep -q $TEST_EXECUTOR; then
  printf "\nA selenium-test-executor image is already installed. No build required.\n"
else
  printf "\nBuilding a selenium-test-executor image.\n"
  docker build -t $TEST_EXECUTOR .
fi

printf "\nSetting up network and containers\n"
docker network create "$NETWORK"
docker run --rm -d --network="$NETWORK" --shm-size="2g" --name "$SELENIUM_SERVER" selenium/standalone-firefox
docker run --rm -d --network="$NETWORK" --name "$TEST_EXECUTOR" "$TEST_EXECUTOR"

printf "\nCopying project files to java test application container\n"
docker cp config.properties "${TEST_EXECUTOR}:/home"
docker cp src "${TEST_EXECUTOR}:/home"
docker cp pom.xml "${TEST_EXECUTOR}:/home"

printf "\nFollowing the test execution\n"
docker logs -f "$TEST_EXECUTOR"

printf "\nCleanup\n"
docker stop selenium
docker network rm "$NETWORK"
