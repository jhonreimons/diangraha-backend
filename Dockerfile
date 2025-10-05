# Gunakan OpenJDK 21 resmi
FROM openjdk:21-jdk-slim

# Set direktori kerja
WORKDIR /app

# Copy file JAR hasil build
COPY target/diangraha-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port aplikasi
EXPOSE 8080

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]
