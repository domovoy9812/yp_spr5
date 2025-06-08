FROM openjdk:21-jdk-slim
WORKDIR /rest-api
COPY ./build/libs/rest-api-0.0.1-SNAPSHOT.jar /rest-api
EXPOSE 8081
CMD ["java", "-jar", "/rest-api/rest-api-0.0.1-SNAPSHOT.jar"]
