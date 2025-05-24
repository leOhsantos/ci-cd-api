FROM maven:3-openjdk-17 as builder

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /build/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]