FROM ubuntu
WORKDIR /home
COPY ./pom.xml .
RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget openjdk-11-jdk maven
RUN mvn install dependency:copy-dependencies
RUN rm pom.xml
CMD sleep 5s && mvn test
