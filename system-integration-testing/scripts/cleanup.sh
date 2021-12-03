#!/bin/bash

cd "$PWD" || exit
. ./init-variables.sh
. ./stop.sh

docker rmi "$TEST_EXECUTOR"
docker rmi "selenium/standalone-firefox"