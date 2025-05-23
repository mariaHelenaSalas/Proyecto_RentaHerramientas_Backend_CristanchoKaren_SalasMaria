# Descripción
## Este sistema permite gestionar el alquiler de herramientas entre proveedores y clientes. 
El backend ha sido desarrollado con Spring Boot, usando arquitectura por capas y con el objetivo de brindar un API RESTful segura, 
modular y escalable.

## Tecnologías Utilizadas                                      
 
- Spring Boot      
- Java 17                                   
- PostgreSQL                             
- JPA (Hibernate)                     
- Maven                       
- Spring Security                     
- JWT                                  
- Git / GitHub                  
- Postman
-                                       
## Entidades Principales (JPA)
- Provider – Datos adicionales de los usuarios proveedores

- Client – Datos adicionales de los usuarios clientes

- Tool – Herramientas disponibles para alquiler

- Category – Categorías de herramientas

- Reservation – Alquileres realizados

- Payment – Pagos por reservas

- Invoice – Facturas generadas

- Notification – Notificaciones enviadas

- Return – Devoluciones de herramientas

History – Historial de eventos

## Seguridad (Authentication & Authorization)

- Spring Security y JWT para autenticación.

- Roles definidos: ADMIN, PROVIDER, CLIENT.

- de rutas según rol usando:

## Endpoints REST (Ejemplos)

- Método
- GET
- POST
- GET
- POST
- POST
