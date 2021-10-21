#!/bin/sh

cd "$(dirname "$0")" || exit
. ./init-variables.sh

if docker ps | grep -q "$TEST_EXECUTOR"; then
  printf "\nSelenium test executor is still running and will therefore be stopped.\n"
  docker stop "$TEST_EXECUTOR"
fi

if docker ps | grep -q "$SELENIUM_SERVER"; then
  printf "\nSelenium server is still running and will therefore be stopped.\n"
  docker stop "$SELENIUM_SERVER"
fi