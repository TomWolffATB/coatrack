#!/bin/sh

#TODO On the one hand the executing 'run.sh' should handle the image in the background. On the other hand an explicit execution of 'build.sh' should replace the old image.

if docker images | grep -q "java-appv2"; then
  echo "Creating an image."
  #execute docker build: docker build -t selenium-tests .
else
  echo "A proper image does already exist. No build required."
fi