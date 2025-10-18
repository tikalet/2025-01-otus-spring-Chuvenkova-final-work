FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
RUN apk add --no-cache maven
COPY pom.xml .
COPY src ./src
RUN mvn clean -DskipTests package

FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /app
COPY --from=build /app/target/*.jar lis-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "lis-app.jar"]
