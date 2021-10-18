#!/bin/sh
#TODO in execute.sh the shebang is missing, which might be why it is not working yet

#Run containers via Shell
#docker network create test-net
docker run --rm -d --network="test-net" --shm-size="2g" --name selenium selenium/standalone-firefox
docker run --rm -d --network="test-net" --name java-appv2 --entrypoint "/home/execute.sh" java-appv2

#Copy project files to docker container
docker cp docker-script.sh java-appv2:/home
docker cp config.properties java-appv2:/home
docker cp src java-appv2:/home
docker cp pom.xml java-appv2:/home

#TODO If Ubuntu image with installed software does not exist, then create one.
#TODO Prepare Ubuntu Image
#DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget openjdk-11-jdk maven
#copy file to container
#mvn compile
#docker commit java-app java-app

#TODO Run java-app, start 'mvn package' automatically, print its console content to host and let it terminate.
#TODO Stop other container and delete the network. Maybe Docker Compose instead?
#Console content: docker logs -f <CONTAINER>

#TODO Extract test report from Docker container to host

#Stop and remove containers
#docker stop java-app
#docker stop selenium