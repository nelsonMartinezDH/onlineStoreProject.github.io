# 🛒 Tienda en Línea - API RESTful

Este proyecto es una **API RESTful desarrollada en Spring Boot** que permite gestionar los elementos clave de una tienda en línea. Ofrece funcionalidades para la administración de usuarios, productos, pedidos, pagos, y más. Está diseñado como un sistema **monolítico**, ideal para aplicaciones de comercio electrónico de pequeña a mediana escala.

## 🚀 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **MySQL / PostgreSQL**
- **Maven**
- **Lombok**
- **Swagger/OpenAPI** (Documentación de la API)

## 📂 Estructura del proyecto

onlineStore/

├── src/

│ ├── main/

│ │ ├── java/

│ │ │ └── com/nelsonmartinez/onlineStore/

│ │ │ ├── controller/

│ │ │ ├── model/

│ │ │ ├── repository/

│ │ │ ├── service/

│ │ │ └── onlineStore.java

│ │ └── resources/

│ │ ├── application.yml

│ │ └── static/

├── pom.xml

└── README.md

## 🧰 Funcionalidades principales

- Registro y autenticación de usuarios con JWT
- Gestión de productos (CRUD)
- Procesamiento de pedidos y detalle de compras
- Simulación de pagos
- Control de stock
- Manejo de errores y validaciones

## 📦 Instalación y ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/tu-repo.git

2. Crea una base de datos llamada ecommerce_db en tu gestor (MySQL o PostgreSQL).

3. Configura tus credenciales en src/main/resources/application.yml.

4. Ejecuta el proyecto desde tu IDE o con:
mvn spring-boot:run

🧪 Pruebas
Puedes usar herramientas como Postman o abrir la documentación Swagger (si está habilitada en tu proyecto) en:
http://localhost:8080/swagger-ui.html


👨‍💻 Autor
Nelson Martinez Hazbum - Ingeniero De Sistemas / Backend Developer
