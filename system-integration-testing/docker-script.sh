#!/bin/sh

#TODO Replace sleep function by let the java application wait until port is reachable like GatewayRunner does.
echo "Sleep"
sleep 5s

echo "Go to /home"
cd /home

echo "Execute Tests"
mvn package #TODO Maybe just 'mvn test' instead?