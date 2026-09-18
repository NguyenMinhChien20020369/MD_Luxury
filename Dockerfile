# ===== Stage 1: Build ứng dụng =====
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw package -DskipTests -B

# ===== Stage 2: Chạy ứng dụng =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Tạo group/user phi root để đảm bảo an toàn
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]