FROM gradle:8.5.0-jdk21-alpine as build
COPY . /home/app
WORKDIR /home/app
RUN gradle bootJar
FROM openjdk:21-jdk-slim
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY --from=build /home/app/build/libs/*.jar app.jar
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app.jar"]