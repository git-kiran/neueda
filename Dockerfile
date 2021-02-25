FROM ubuntu:18.04

RUN apt-get clean && apt-get update -y
RUN apt-get -y install --fix-missing make unzip \
                       vim lzip gawk tar file \
                       git wget
WORKDIR /ws
ARG JAR_FILE
COPY target/neueda-*.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]