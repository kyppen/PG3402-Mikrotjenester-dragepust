
FROM maven:3.9.4-amazoncorretto-17 AS builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests
#RUN mvn clean package

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
# copy Item packages into image
COPY src/main/resources/*.json src/main/resources/sets/


EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
