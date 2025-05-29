# 🛠️ Tool Rental System 

Sistema web para la **gestión de alquiler de herramientas** desarrollado como proyecto final educativo. Este backend ofrece una API RESTful que permite el registro, autenticación, administración y operaciones de negocio como reservas, pagos, devoluciones y facturación.

---

## 🚀 Tecnologías utilizadas

- **Lenguaje:** Java 21
- **Framework:** Spring Boot
- **Seguridad:** Spring Security + JWT
- **Base de datos:** PostgreSQL
- **Documentación:** Swagger
- **Otras librerías:** iTextPDF (facturación PDF)

---

## ⚙️ Clonación, instalación y ejecución

```bash
# Clona el repositorio
git clone https://github.com/mariaHelenaSalas/Proyecto_RentaHerramientas_Backend_CristanchoKaren_SalasMaria.git
cd Proyecto_RentaHerramientas_Backend_CristanchoKaren_SalasMaria
```
```bash
# Clona el repositorio
git clone https://github.com/KarenLore/Proyecto_RentaHerramientas_Fronted_CristanchoKaren_SalasMaria.git
cd Proyecto_RentaHerramientas_Frontend_CristanchoKaren_SalasMaria
```

# Asegúrate de tener Java 21 y PostgreSQL ejecutándose
# Ejecuta el proyecto (Spring Boot - Maven)
./mvnw spring-boot:run

## 🛠️ Archivo de configuración
### Ejemplo de application-dev.properties:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/toolrentdb
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update


- JWT
application.security.jwt.secret=secret
application.security.jwt.expiration=86400000

- CORS
application.cors.allowed-origins=http://localhost:4200

- Logging
logging.level.org.springframework=INFO
```
## 🗂️ Diagrama relacional de la base de datos
![Untitled](https://github.com/user-attachments/assets/2060bc7b-304c-4e02-bedd-b95fbfa1a0f1)


## 📌 Endpoints principales
| Método | Endpoint                      | Descripción                    |
| ------ | ----------------------------- | ------------------------------ |
| POST   | `/api/auth/register`          | Registro de usuario            |
| POST   | `/api/auth/login`             | Login y obtención de token JWT |
| GET    | `/api/tools`                  | Listado de herramientas        |
| POST   | `/api/reservations`           | Crear una reserva              |
| GET    | `/api/invoices/{id}/download` | Descargar factura en PDF       |
| GET    | `/api/notifications`          | Ver notificaciones del usuario |
| DELETE | `/api/users/{id}`             | Eliminar usuario (rol Admin)   |

Puedes probarlos directamente desde Swagger:
http://localhost:8080/swagger-ui/index.html

## 🔐 Autenticación y roles
### JWT (JSON Web Token):
- Se genera al hacer login con /api/auth/login
- Se debe incluir en el header: Authorization: Bearer {token}
## Roles del sistema:
  - ADMIN: gestiona usuarios, categorías, herramientas y facturación
  - PROVIDER: publica herramientas y gestiona reservas
  - CLIENT: puede reservar y ver sus facturas
## 🧪 Guía para pruebas
### ✅ Pruebas manuales

1. Inicia el backend (localhost:8080)

2. Usa Postman o Swagger para registrar, loguear, crear reservas, pagos y generar facturas.

3. Puedes simular flujos como:

 - Registro/Login

  - Alquiler de herramienta

 - Descarga de factura en PDF
## 📁 Estructura del proyecto
```bash
📦 proyecto-renta
 ┣ 📂 application.services        -Lógica de negocio
 ┣ 📂 domain.entities             - Entidades JPA
 ┣ 📂 domain.dtos                 - Objetos de transferencia
 ┣ 📂 infrastructure.controllers  - Endpoints REST
 ┣ 📂 infrastructure.repositories - Repositorios JPA
 ┣ 📂 infrastructure.security     - JWT y seguridad
 ┣ 📂 utils                       - Generación de PDF
 ┣ 📄 application.properties
 ┗ 📄 pom.xml
```
## 👩‍💻 Admin Predeterminado (Se crea al momento de ejercutar el proyecto)
✅ Email: karen@gmail.com
✅ Password: 1234567890

## 👩‍💻 Autores

 💻 Karen Cristancho

 💻 María Helena Salas



