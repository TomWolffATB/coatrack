FROM ubuntu
WORKDIR /home
COPY ./docker-script.sh .
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget openjdk-11-jdk maven && chmod +x /home/docker-script.sh
ENTRYPOINT ["/home/docker-script.sh"]
