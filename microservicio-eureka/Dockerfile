FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn clean deploy -DskipTests=true -DaltDeploymentRepository=internal.repo::default::file:/root/.m2/repository

FROM openjdk:17-jdk-alpine
EXPOSE 8761
WORKDIR /app

COPY --from=builder /app/target/microservicio-eureka.jar /app/microservicio-eureka.jar

CMD ["java", "-jar", "microservicio-eureka.jar"]
