# Documentación del Proyecto Backend para Panda, Empresa de Transportes

## Descripción del Proyecto
Este proyecto backend desarrollado en Spring Boot está diseñado para gestionar registros de trabajadores, conductores, camiones, carretas, facturas, guias de transportista, citas y usuarios para una empresa de transportes. Ofrece un conjunto de funcionalidades que incluyen seguridad, transacción de datos, conexión a servicios externos de la Reniec y Sunat, documentación con Swagger, control de excepciones, acceso a base de datos PostgreSQL, pruebas unitarias y generación de reportes en formato PDF.

## Características Principales
- Seguridad: Implementada utilizando Spring Security y JWT (JSON Web Tokens), con control de acceso para diferentes tipos de usuarios.
- Transacción de Datos: Utiliza Spring Data con Hibernate para la gestión eficiente de la base de datos.
- Conexión a APIs Externas: Integración con servicios de la Reniec y Sunat para obtener información adicional sobre usuarios y conductores.
- Documentación: Generada automáticamente con Swagger para una API clara y fácil de entender.
- Control de Excepciones: Manejo de errores y excepciones para garantizar la estabilidad del sistema.
- Base de Datos: Utiliza PostgreSQL como base de datos relacional para almacenar de forma segura y eficiente los datos del sistema.
- Pruebas Unitarias: Incluye pruebas unitarias implementadas con JUnit y Mockito para asegurar la calidad del código.
- Generación de Reportes: Permite la creación de informes en formato PDF utilizando JasperReports-Fonts.
- Generación de Facturas: Capacidad para generar facturas para los servicios de transporte realizados, proporcionando un registro detallado de los servicios prestados y los costos asociados.
- Generación de Guías de Transportista: Permite la creación de guías de transporte para documentar el movimiento de bienes durante el transporte, incluyendo información sobre la carga, el origen y el destino.
## Tecnologías Utilizadas
- Spring Boot: Framework de desarrollo de aplicaciones Java.
- Spring Security: Proporciona autenticación y autorización para aplicaciones Spring.
- JWT (JSON Web Tokens): Utilizado para la autenticación segura de usuarios.
- Spring Data: Facilita el acceso y manipulación de datos utilizando el modelo de programación basado en Spring.
- Hibernate: Framework de mapeo objeto-relacional para Java.
- Swagger: Herramienta para generar documentación de API de forma automática.
- PostgreSQL: Sistema de gestión de bases de datos relacional.
- JUnit y Mockito: Frameworks de pruebas unitarias para Java.
- JasperReports-Fonts: Biblioteca para la generación de informes en formato PDF.
- Openfeign: Para el consumo de apis externas.
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,idea,postgres,postman,docker,git&perline=14" />
  </a>
</p>
## Requisitos de Instalación y Uso
Para ejecutar este proyecto en su entorno local, asegúrese de tener instalado lo siguiente:

- Java JDK (preferiblemente versión 8 o superior)
- Maven
- PostgreSQL (con base de datos creada)
- Git (opcional)
## Instrucciones de Instalación
1. Clonar el repositorio desde GitHub o descargar el código fuente.
2. Configurar la base de datos PostgreSQL según las especificaciones del archivo de configuración.
3. Compilar el proyecto utilizando Maven: mvn clean install.
4. Ejecutar la aplicación Spring Boot: java -jar nombre_del_archivo.jar.
5. La aplicación estará disponible en http://localhost:puerto.
## Contribución
Si desea contribuir a este proyecto, por favor abra un problema o envíe una solicitud de extracción en GitHub.


## Contacto
Para más información, por favor póngase en contacto con Jefferson Panta en Pantajefferson173@gmail.com.
