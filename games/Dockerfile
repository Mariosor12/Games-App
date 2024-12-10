# Usa la imagen de Gradle como base para construir el proyecto  
FROM gradle:7.6.0-jdk17 AS builder  

# Establece el directorio de trabajo en /games  
WORKDIR /games  

# Copia los archivos de configuración necesarios para Gradle al contenedor  
COPY settings.gradle .  
COPY build.gradle .  
COPY gradlew .  
COPY gradle ./gradle   
COPY src ./src  

# Instala Node.js y npm  
RUN apt-get update && apt-get install -y \
    curl \
    && curl -sL https://deb.nodesource.com/setup_16.x | bash - \
    && apt-get install -y nodejs \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*  

# Verifica que el wrapper de Gradle sea ejecutable  
RUN chmod +x ./gradlew  

# Ejecución del comando para instalar las dependencias de npm primero antes de construir  
RUN npm install --prefix ./src/main/frontend  

# Ejecuta la construcción del proyecto, incluyendo el frontend  
RUN ./gradlew clean build --no-daemon  

# Usa una imagen de OpenJDK para crear la imagen final  
FROM openjdk:17-slim  

# Establece el directorio de trabajo en /games  
WORKDIR /games  

# Copia el archivo JAR construido desde la fase de construcción  
COPY --from=builder /games/build/libs/games-0.0.1-SNAPSHOT.jar /games/app.jar  

# Expone el puerto en el que se ejecutará la aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación en el contenedor  
CMD ["java", "-jar", "/games/app.jar"]