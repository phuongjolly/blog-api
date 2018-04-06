

FROM maven:3.5.2-jdk-8 as build
VOLUME /root/.m2
WORKDIR /repo
ADD . /repo
RUN mvn clean package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /repo/target/blog-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar", "--spring.profiles.active=production"]
