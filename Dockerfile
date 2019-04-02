FROM ubuntu:18.04

RUN \
  apt-get update -y && \
  apt-get -q install -y curl && \
  curl -L https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u202-b08/OpenJDK8U-jdk_x64_linux_hotspot_8u202b08.tar.gz -o jdk.tar.gz && \
  tar zxvf jdk.tar.gz && \
  mv jdk8u202-b08 java &&
  curl -L http://ftp.kddilabs.jp/infosystems/apache/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz -o maven.tar.gz && \
  tar zxvf maven.tar.gz && \
  mv apache-maven-3.6.0 maven && \
  rm jdk.tar.gz && \
  rm maven.tar.gz && \
  groupadd -r build && \
  useradd -m -g build mvn


ENV JAVA_HOME /java
ENV PATH=$PATH:java/bin:/maven/bin
ENV MAVEN_HOME /maven

USER mvn
WORKDIR /home/mvn

EXPOSE 8080

CMD ["/bin/bash"]
