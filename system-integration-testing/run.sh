#!/bin/sh

NETWORK="selenium-test-network"
TEST_EXECUTOR="selenium-test-executor"
SELENIUM_SERVER="selenium-server"

if docker ps | grep -q "$TEST_EXECUTOR"; then
  printf "\nSelenium test executor is still running and will therefore be stopped.\n"
  docker stop $TEST_EXECUTOR
fi

if docker ps | grep -q "$SELENIUM_SERVER"; then
  printf "\nSelenium server is still running and will therefore be stopped.\n"
  docker stop $SELENIUM_SERVER
fi

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

if docker network ls | grep -q "$NETWORK"; then
  printf "\nNetwork is already set up.\n"
else
  printf "\nSetting up network.\n"
  docker network create "$NETWORK"
fi

printf "\nSetting up Selenium server\n"
docker run --rm -d --network="$NETWORK" --shm-size="2g" --name "$SELENIUM_SERVER" selenium/standalone-firefox

printf "\nSetting up Selenium test executor\n"
docker run --rm -d --network="$NETWORK" --name "$TEST_EXECUTOR" "$TEST_EXECUTOR"

printf "\nCopying project files to java test application container\n"
docker cp config.properties "${TEST_EXECUTOR}:/home"
docker cp src "${TEST_EXECUTOR}:/home"
docker cp pom.xml "${TEST_EXECUTOR}:/home"

printf "\nPrinting the test execution logs:\n\n"
docker logs -f "$TEST_EXECUTOR"

printf "\nCleanup Selenium Server \n"
docker stop selenium

printf "\nCleanup network \n"
docker network rm "$NETWORK"
