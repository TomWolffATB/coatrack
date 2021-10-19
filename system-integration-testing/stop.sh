#!/bin/sh

#TODO Maybe add a cleanup script which stops containers, removes all images and the network.

#TODO Resolve code duplication of variables with run.sh
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