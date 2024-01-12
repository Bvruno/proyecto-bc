# API de Productos

Este proyecto es una API para la gestión de productos desarrollada con Spring Boot y MongoDB.

## Configuración del Proyecto

### Dependencias Principales

- **Spring Boot Starter Parent:** Utilizado para la gestión de dependencias y configuraciones del proyecto. Actualmente, la versión utilizada es 3.2.0.
- **Java Version:** El proyecto está configurado para usar Java 17.
- **Spring Cloud:** La versión utilizada es 2023.0.0.

### Dependencias

- **spring-boot-starter-data-mongodb-reactive:** Para la interacción reactiva con MongoDB.
- **spring-boot-starter-web y spring-boot-starter-webflux:** Proporcionan soporte para la creación de endpoints web reactivos.
- **spring-boot-devtools:** Herramientas para desarrollo, habilitadas en tiempo de ejecución.
- **lombok:** Biblioteca opcional para la reducción de código boilerplate.
- **spring-boot-starter-test y reactor-test:** Dependencias para las pruebas unitarias y de integración.

## Configuración

### Propiedades de la Aplicación

- **Spring Application Name:** `api-productos`
- **Base de Datos MongoDB URI:** `mongodb://localhost:27017/proyectoBC`
- **Puerto del Servidor:** Por defecto, se utiliza el puerto `8090`.
- **Context Path de la Servlet:** `/api/productos`
- **Niveles de Logging Configurados:**
  - `root`: INFO
  - `org.springframework.web`: ERROR
  - `com.miapp.paquete`: DEBUG

## Componentes Principales

### Controladores

- **ProductoController:** Define los endpoints para la gestión de productos utilizando métodos HTTP estándar (GET, POST, PUT, DELETE).

### Repositorio

- **ProductoRepository:** Interfaz que extiende `ReactiveMongoRepository` para operaciones CRUD en la base de datos MongoDB.

### Servicios

- **ProductoServices:** Contiene la lógica de negocio para la gestión de productos, interactuando con el repositorio.

### Pruebas

Se han incluido pruebas unitarias utilizando `WebTestClient` para el controlador y servicios.

## Ejecución del Proyecto

El proyecto puede ser ejecutado como una aplicación Spring Boot estándar. Asegúrate de tener un entorno configurado con Java 17 y MongoDB instalado.
