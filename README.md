# Speed-to-text Service

Application Spring Boot with IBM Watson Assistant service.

This service belong to MLGIA.

This service is configured to start in port 8081. It connect to IBM SpeechToText Service, so it's required set the credentials to access to it.

API exposed:
[POST] /audio


### Build Docker image
```
mvn clean package docker:build
```
