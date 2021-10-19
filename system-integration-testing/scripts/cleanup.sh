#!/bin/sh

NETWORK="selenium-test-network"
TEST_EXECUTOR="selenium-test-executor"

sh stop.sh

docker network rm $NETWORK
docker rmi $TEST_EXECUTOR
docker rmi "selenium/standalone-firefox"