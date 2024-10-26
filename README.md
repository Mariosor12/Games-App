# Games-App  

¡Bienvenido a Games-App! 🎮  

Games-App es una aplicación diseñada para los amantes de los videojuegos. Permite a los usuarios ver una lista de juegos, agregar nuevos títulos y gestionar una lista de favoritos. Esta aplicación utiliza Java con Spring Boot en el backend y Vaadin en el frontend para una experiencia de usuario fluida y moderna.  

## Características  

- Visualiza una lista de juegos disponibles.  
- Agrega nuevos juegos a la lista.  
- Mantén un registro de tus juegos favoritos.  
- Interactúa con una API REST que utiliza Json Server para obtener datos de juegos y usuarios.  

## Estructura del Proyecto  
/Games-App
- /games # Lógica del backend en Java (Spring Boot)
- /json # Carpeta que contiene Json Server y su configuración

## Tecnologías Utilizadas  

- **Backend**: Java, Spring Boot  
- **Frontend**: Vaadin  
- **Base de Datos**: Json Server (simula una API REST para la gestión de datos)  

## Instalación  

Sigue estos pasos para configurar el proyecto en tu máquina local:  

1. **Clona el repositorio:**  
   ```bash  
   git clone https://github.com/tu-usuario/Games-App.git  
   cd Games-App
2. **Configura Json Server:**
   Asegúrate de tener json-server instalado. Si no lo tienes, puedes instalarlo a través de npm:
   ```bash
   npm install -g json-server
3. **Inicia Json Server:**
   Cambia a la carpeta json y crea un archivo db.json con la estructura de datos necesaria. Luego ejecuta:
   ```bash
   cd json  
   json-server --watch db.json

4. **Construye y ejecuta el Backend:**
   Cambia a la carpeta games y construye y ejecuta la aplicación Spring Boot:
   ```bash
   cd ../games  
   ./gradlew build  
   ./gradlew bootRun
5. **Abre la aplicación:**
   Accede a la aplicación a través de tu navegador en http://localhost:8080.

## Licencia
Este proyecto está bajo la Licencia MIT - consulta el archivo LICENSE para más detalles.

¡Diviértete usando Games-App!
