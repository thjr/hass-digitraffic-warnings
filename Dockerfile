ARG BUILD_FROM
FROM $BUILD_FROM

COPY target/warnings-0.1-runner.jar /app.jar

# install java
RUN \
    apk add --no-cache \
        openjdk11-jre --repository=http://dl-cdn.alpinelinux.org/alpine/edge/main
    && rm -fr /tmp/*

CMD [ "/usr/bin/java", "-jar", "/app.jar" ]

