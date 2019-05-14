FROM openjdk:11-jdk-alpine

MAINTAINER PrzodownikR1 <przodownikR1@gmail.com>

VOLUME /tmp

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

RUN  apk update && apk upgrade && apk add netcat-openbsd bash curl shadow ca-certificates && update-ca-certificates

ARG opuser=tms
ARG opuid=10000
RUN groupadd --gid $opuid $opuser && \
    useradd -m --comment "HIT Operations" --shell /bin/bash \
            --gid $opuid --uid $opuid $opuser

USER $opuser

ENV CKPL_PROJECT_JVM_ARGS=" \
 -server \
 -XX:+UseConcMarkSweepGC"

EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","pl.scalatech.wiremock.Application"]

