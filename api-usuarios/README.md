# API Usuarios - Proyecto para BC

Este proyecto consiste en una API para gestionar usuarios, incluyendo diversas funcionalidades como:

- Obtener todos los usuarios
- Encontrar usuarios por DNI
- Validar DNI
- Validar teléfono y crear códigos de validación
- Validar códigos de teléfono y email
- Crear usuarios
- Editar usuarios
- Deshabilitar usuarios por DNI

## Endpoints

### Obtener todos los usuarios
- **GET** `/listarUsuarios`

### Encontrar usuario por DNI
- **GET** `/{dni}`

### Validar DNI
- **POST** `/validarDNI`

### Validar teléfono y crear código
- **POST** `/validarTelefono/crearCodigo`

### Validar códigos de teléfono o email
- **POST** `/validarTelefono/validarCodigo`
- **POST** `/validarEmail/validarCodigo`

### Crear usuario
- **POST** `/crearUsuario`

### Editar usuario
- **PUT** `/editar`

### Deshabilitar usuario por DNI
- **PUT** `/deshabilitar/{dni}`

## Configuración

### Tecnologías utilizadas

- Java 17
- Spring Boot 3.2.0
- MongoDB Reactive
- Spring Cloud Netflix Eureka Client
- Twilio SDK 8.30.0

### Dependencias principales

- `spring-boot-starter-data-mongodb-reactive`
- `spring-boot-starter-web`
- `spring-boot-starter-webflux`
- `spring-cloud-starter-netflix-eureka-client`
- `twilio`
- `spring-security-crypto`

### Configuración adicional

El proyecto cuenta con configuraciones para la base de datos MongoDB, manejo de errores, logging, integración con Eureka para descubrimiento de servicios y definiciones de URLs para servicios externos como APIs de `apis-net` y Twilio.

---

Este proyecto utiliza Maven como sistema de gestión de proyectos y construcción.

## Uso

Para ejecutar la aplicación, puedes utilizar el plugin de Maven:

```bash
mvn spring-boot:run
```
Este archivo Markdown contiene información sobre los endpoints disponibles, las tecnologías utilizadas, las dependencias principales y la configuración del proyecto. ¡Listo para ser usado como README!
