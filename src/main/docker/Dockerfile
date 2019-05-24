FROM frolvlad/alpine-java

VOLUME /tmp

ADD mlgia-speech-to-text-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]