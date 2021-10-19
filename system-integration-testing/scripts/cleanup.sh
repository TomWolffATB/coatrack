#!/bin/sh

. ./init-variables.sh

sh stop.sh

docker network rm "$NETWORK"
docker rmi "$TEST_EXECUTOR"
docker rmi "selenium/standalone-firefox"