#!/bin/sh

echo "Sleep"
sleep 5s

echo "Go to /home"
cd /home

echo "Execute Tests"
mvn package