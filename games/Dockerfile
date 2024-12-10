# Usa la imagen de Gradle como base para construir el proyecto  
FROM gradle:7.6.0-jdk17 AS builder  

WORKDIR /games  

# Copia solo los archivos necesarios para la construcción  
COPY build.gradle gradle-wrapper.properties settings.gradle ./  
COPY src ./src  

# Verifica que el wrapper de Gradle sea ejecutable  
RUN chmod +x ./gradlew  

# Construye el proyecto, incluyendo el frontend  
RUN ./gradlew clean build --no-daemon  

# Usa una imagen de OpenJDK para crear la imagen final  
FROM openjdk:17-slim  

WORKDIR /games  

# Copia el JAR desde la fase de construcción  
COPY --from=builder /games/build/libs/games-0.0.1-SNAPSHOT.jar /games/app.jar  

# Expone el puerto  
EXPOSE 8080  

# Comando para ejecutar la aplicación  
CMD ["java", "-jar", "/games/app.jar"]