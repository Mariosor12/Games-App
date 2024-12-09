# Usa una imagen de Gradle para compilar tu aplicación  
FROM gradle:7.6.0-jdk17 AS builder  

# Establece el directorio de trabajo  
WORKDIR /games  

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

# Asegúrate de listar las bibliotecas generadas  
RUN ls -la build/libs  

# Usa una imagen base de OpenJDK para ejecutar la aplicación  
FROM openjdk:17-slim   

WORKDIR /games  

# Crea el directorio de destino  
RUN mkdir -p /games/build/libs  

# Copia el JAR compilado desde la etapa de construcción  
COPY --from=builder /games/build/libs/games-0.0.1-SNAPSHOT.jar /games/games-0.0.1-SNAPSHOT.jar  

# Expone el puerto donde se ejecutará tu aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación  
CMD ["java", "-jar", "games-0.0.1-SNAPSHOT.jar"]