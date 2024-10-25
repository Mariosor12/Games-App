# Usa una imagen de Gradle para compilar tu aplicación  
FROM gradle:7.6.0-jdk17 AS builder  

# Establece el directorio de trabajo  
WORKDIR /app/games  

# Copia el archivo de configuración de Gradle y el wrapper  
COPY games/settings.gradle .  
COPY games/build.gradle .  
COPY games/gradlew .  
COPY games/gradle ./gradle 

# Copia el código fuente de la aplicación  
COPY games/src ./src  

# Asegúrate de que el wrapper de Gradle es ejecutable  
RUN chmod +x ./gradlew  

# Compila la aplicación  
RUN ./gradlew build --no-daemon  

# Usa una imagen base de OpenJDK para ejecutar la aplicación  
FROM openjdk:17-slim    

# Copia el JAR compilado desde la etapa de construcción  
COPY build/libs games-0.0.1-SNAPSHOT.jar  

# Expone el puerto donde se ejecutará tu aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación  
CMD ["java", "-jar", "games-0.0.1-SNAPSHOT.jar"]