FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./applications/app-service/build/libs/authentication.jar .

# Directorio para llaves JWT
RUN mkdir -p /keys
VOLUME /keys

EXPOSE 8080
ENTRYPOINT ["java","-jar","authentication.jar"]
