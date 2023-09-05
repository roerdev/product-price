# Microservicio de Gestión de Precios

Este microservicio de gestión de precios está diseñado para administrar y consultar información de precios. A continuación, se describen los requisitos para desplegar la aplicación, una breve explicación de cada endpoint y cómo acceder a la documentación de la API a través de Swagger.

## Requisitos para Desplegar la Aplicación

Para desplegar y ejecutar esta aplicación, debes tener instalado lo siguiente:

- Java Development Kit (JDK) 11 o superior
- Maven
- [H2 Database](https://www.h2database.com) (se utiliza una base de datos H2 en memoria para este ejemplo)

## Endpoints de la API

El microservicio proporciona los siguientes endpoints de la API para gestionar precios:

- **GET /prices**: Obtiene todos los precios disponibles en la base de datos.
- **GET /prices/{id}**: Obtiene un precio específico por su ID.
- **GET /prices/{date}/{product}/{chain}**: Obtiene un precio específico para la fecha, el producto y la cadena indicada.
- **POST /prices**: Crea un nuevo precio.
- **PUT /prices/{id}**: Actualiza un precio existente por su ID.
- **DELETE /prices/{id}**: Elimina un precio existente por su ID.

Cada endpoint espera y devuelve datos en formato JSON. Asegúrate de proporcionar los datos necesarios en el cuerpo de la solicitud en el formato correcto.

## Documentación de la API (Swagger)

Puedes acceder a la documentación detallada de la API utilizando Swagger. Sigue estos pasos:

1. Inicia la aplicación en tu entorno de desarrollo.
2. Abre un navegador web y ve a la siguiente URL: [http://localhost:8081/swagger-ui.html#/](http://localhost:8081/swagger-ui.html#/)
3. Desde la interfaz de Swagger, podrás explorar y probar todos los endpoints disponibles en la API. También encontrarás descripciones detalladas de cada endpoint y los datos de solicitud y respuesta esperados.

## Docker

El microservicio se ha creado de tal manera que puedas levantarlo en docker mediante **compose-up.bat** que se encuentra en la raiz del proyecto. Este archivo solo ejecuta el **docker compose up -d --build** para crear el contenedor. Para esto se requiere tener instalado Docker Compose en la maquina donde se desea desplegar el contenedor.

## Levantar la Aplicación

Para levantar la app puedes hacerlo por Docker, desde un IDE (Ej: Intellij IDEA), o tambien puedes compilar la app con mvn y ejecutarla con Java usando el comando **java -jar target/product-price-0.0.1-SNAPSHOT.jar**

## Pruebas Integradas

Se adjunta dentro de la carpeta **/PostmanCollection** que se encuentra en la raiz del proyecto, la collection de Postman para realizar las pruebas solicitadas a continuación:

- Test 1: request at 10:00 a.m. on the 14th for product 35455 for brand 1 (XYZ)
- Test 2: request at 4:00 p.m. on the 14th for product 35455 for brand 1 (XYZ)
- Test 3: request at 9:00 p.m. on day 14th for product 35455 for brand 1 (XYZ)
- Test 4: request at 10:00 a.m. on the 15th for product 35455 for brand 1 (XYZ)
- Test 5: request at 9:00 p.m. on day 16th for product 35455 for brand 1 (XYZ)

Se han desarrollado los respectivos test para validar las respuuestas de la API.

**Se debe tener levantado el microservicio antes de ejecutar la collection de Postman**


¡Disfruta explorando y utilizando el microservicio de gestión de precios!    
