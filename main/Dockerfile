FROM openjdk:21-jdk-slim
WORKDIR /app
COPY ./build/libs/main-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
EXPOSE 8082
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8082", "-jar", "/app/main-0.0.1-SNAPSHOT.jar"]
