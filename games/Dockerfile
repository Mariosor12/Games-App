# Usar una imagen base de Java  
FROM openjdk:11-jre-slim  

# Establecer el directorio de trabajo  
WORKDIR /app  

# Copiar el JAR de la aplicación y el archivo JSON al contenedor   
COPY db.json .   

# Instalar JSON Server  
RUN apt-get update && apt-get install -y nodejs npm \
    && npm install -g json-server  

# Exponer los puertos  
EXPOSE 8080 3000  

# Comando para ejecutar JSON Server y luego la aplicación Spring Boot  
CMD json-server --watch db.json --port 3000