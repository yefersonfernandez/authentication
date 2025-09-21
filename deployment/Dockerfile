FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./applications/app-service/build/libs/authentication.jar .

EXPOSE 8080
ENTRYPOINT ["java","-jar","authentication.jar"]
