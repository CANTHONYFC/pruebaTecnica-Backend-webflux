FROM amazoncorretto:11.0.13
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir /uploads
ENTRYPOINT ["/usr/bin/java", "-jar", "/app.jar"]