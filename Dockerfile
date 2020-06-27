ARG BUILD_FROM
FROM $BUILD_FROM

COPY target/warnings-0.1.jar /app.jar

# install java
RUN \
    apk add --no-cache \
        openjdk11-jre \
    && rm -fr /tmp/*

CMD [ "/usr/bin/java", "-jar", "/app.jar" ]

