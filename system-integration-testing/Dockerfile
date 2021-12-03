FROM alpine
WORKDIR /home
COPY ./pom.xml .
RUN apk update
RUN apk add curl openjdk11 maven
RUN mvn install dependency:copy-dependencies
RUN rm pom.xml
CMD sleep 5s && sh /home/entrypoint.sh
