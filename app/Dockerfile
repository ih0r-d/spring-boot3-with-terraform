FROM openjdk:21-jdk-slim AS builder
WORKDIR app
COPY . .
RUN ./mvnw clean package -DskipTests


FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/app.jar /app/app.jar
CMD java -Xms1024m -Xmx4024m -jar /app/app.jar
