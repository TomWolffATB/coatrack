#!/bin/bash

BROWSER=$1

set -e

if [ "$BROWSER" == "" ]; then
  BROWSER="firefox"
fi
#TODO To be implemented for firefox, edge, chrome and opera

cd "$PWD" || exit
. ./init-variables.sh
. ./stop.sh

if docker images | grep -q "selenium/standalone-${BROWSER}"; then
  printf "\nA selenium server image for '%s' browser is already installed. No pull required.\n" ${BROWSER}
else
  printf "\nSelenium server image for '%s' browser is pulled.\n" ${BROWSER}
  docker pull "selenium/standalone-${BROWSER}"
fi

if docker images | grep -q "$TEST_EXECUTOR"; then
  printf "\nA selenium-test-executor image is already installed. No build required.\n"
else
  printf "\nBuilding a selenium-test-executor image.\n"
  docker build -t "$TEST_EXECUTOR" ..
fi

printf "\nSetting up Selenium server\n"
docker run --rm -d --network="$NETWORK" --shm-size="2g" --name "$SELENIUM_SERVER" selenium/standalone-${BROWSER}

printf "\nSetting up Selenium test executor\n"
docker run --rm -d --network="$NETWORK" --name "$TEST_EXECUTOR" -e BROWSER="$BROWSER" "$TEST_EXECUTOR"

printf "\nCopying project files to java test application container\n"
cd .. || exit
docker cp config.properties "${TEST_EXECUTOR}:/home"
docker cp src "${TEST_EXECUTOR}:/home"
docker cp pom.xml "${TEST_EXECUTOR}:/home"

printf "\nPrinting the test execution logs:\n\n"
docker logs -f "$TEST_EXECUTOR"

cd scripts
. ./stop.sh
