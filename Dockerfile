# Usa una imagen de Gradle para compilar tu aplicación con JDK 17  
FROM gradle:7.6.0-jdk17 AS builder  

# Establece el directorio de trabajo  
WORKDIR /app/games  

# Copia el archivo de configuración de Gradle  
COPY games/settings.gradle .  
COPY games/build.gradle .  

# Copia el código fuente de la aplicación  
COPY games/src ./src  

# Compila la aplicación  
RUN gradle build --no-daemon  

# Usa una imagen base de OpenJDK para ejecutar la aplicación  
FROM openjdk:17-slim  

# Establece el directorio de trabajo  
WORKDIR /app  

# Copia el JAR compilado desde la etapa de construcción  
COPY --from=builder /app/games/build/libs/games-0.0.1-SNAPSHOT.jar app.jar  

# Expone el puerto donde se ejecutará tu aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación  
CMD ["java", "-jar", "app.jar"]