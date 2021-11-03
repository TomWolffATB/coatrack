#!/bin/sh

cd "$(dirname "$0")" || exit
. ./init-variables.sh
. ./stop.sh

docker rmi "$TEST_EXECUTOR"
docker rmi "selenium/standalone-firefox"