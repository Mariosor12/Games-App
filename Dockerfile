# Usa una imagen de Gradle para construir la aplicación  
FROM gradle:7.6.0-jdk17 AS builder  

# Establece el directorio de trabajo  
WORKDIR /games  

# Copia archivos de configuración y fuentes de la aplicación  
COPY games/settings.gradle .  
COPY games/build.gradle .  
COPY games/gradlew .  
COPY games/gradle ./gradle   
COPY games/src ./src  

# Asegúrate de que el wrapper de Gradle sea ejecutable  
RUN chmod +x ./gradlew  

# Compila la aplicación  
RUN ./gradlew build --no-daemon  

# Usa una imagen base de OpenJDK para ejecutar la aplicación  
FROM openjdk:17-slim  

# Establece el directorio de trabajo  
WORKDIR /games  

# Copia el archivo JAR compilado desde la etapa de construcción  
COPY --from=builder /games/build/libs/games-0.0.1-SNAPSHOT.jar /games/games-0.0.1-SNAPSHOT.jar  

# Expone el puerto donde se ejecutará tu aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación  
CMD ["sh", "-c", "java -jar games-0.0.1-SNAPSHOT.jar"]