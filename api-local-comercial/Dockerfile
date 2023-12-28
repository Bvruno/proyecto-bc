FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn clean deploy -DskipTests=true -DaltDeploymentRepository=internal.repo::default::file:/root/.m2/repository

FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY --from=builder /app/target/api-local-comercial.jar /app/api-local-comercial.jar

CMD ["java", "-jar", "api-local-comercial.jar"]
