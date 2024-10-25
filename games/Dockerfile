# Usa una imagen de Gradle para compilar tu aplicación  
FROM gradle:7.6.0-jdk11 AS builder  

# Copia los archivos del proyecto a la ruta de trabajo  
COPY . /app  

# Establece el directorio de trabajo  
WORKDIR /app/games  

# Compila la aplicación  
RUN gradle build --no-daemon  

# Usa una imagen base de Java para ejecutar la aplicación  
FROM openjdk:11-jre-slim  

# Establece el directorio de trabajo  
WORKDIR /app  

# Copia el JAR compilado desde la etapa de construcción  
COPY --from=builder /app/games/build/libs/games-0.0.1-SNAPSHOT.jar app.jar  

# Expone el puerto donde se ejecutará tu aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación  
CMD ["java", "-jar", "app.jar"]