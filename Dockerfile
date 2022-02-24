FROM alpine:latest
RUN apk update && apk upgrade && apk add openjdk11-jre-headless
COPY ./target/prjname-0.0.1-SNAPSHOT.jar /usr/src/
WORKDIR /usr/src/openapi
CMD ["java", "-Dspring.profiles.active=production", "-jar", "prjname-0.0.1-SNAPSHOT.jar"]
