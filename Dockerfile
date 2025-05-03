FROM maven:3.8.6-amazoncorretto-17 AS maven
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=/build/target/*.jar
RUN mkdir -p /app/wallet
COPY --from=maven $JAR_FILE /app/wallet/wallet.jar
ENTRYPOINT ["java", "-jar", "/app/wallet/wallet.jar"]