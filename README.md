
<p align = "center"> <img src = "https://res.cloudinary.com/djarwlymo/image/upload/v1683163856/images/mailimage_rpy6ow.png" /> </p>
<h1>Implementación de autenticación con JWT con verificación de Email para activación de cuentas.</h1>
<p>El objetivo de este proyecto es proporcionar una guía práctica sobre cómo implementar una funcionalidad de autenticación segura y un registro con verificación de correo electrónico para activar la cuenta de usuario. Se utiliza JWT para generar y validar tokens de autenticación, lo que permite a los usuarios autenticarse y acceder a recursos protegidos en la aplicación.<p>
<br />
<h2>Funcionalidades:</h2>

- Registro de usuario y login con JWT authentication: los usuarios pueden registrarse en el sistema y luego iniciar sesión utilizando la autenticación basada en JSON Web Tokens (JWT).
- Envío de mails utilizando la librería de java Mail para la activación de la cuenta: se envía un correo electrónico al usuario con un enlace para activar su cuenta después del registro.
- Encriptación de contraseña utilizando BCrypt: las contraseñas de los usuarios se encriptan antes de ser almacenadas en la base de datos utilizando la función hash BCrypt para garantizar la seguridad de las contraseñas.
- Autorización basada en roles con Spring Security: se implementa un sistema de roles basado en Spring Security que permite restringir el acceso a ciertas funciones y recursos del sistema según el rol del usuario.

<br />
<h2>Tecnologías:</h2>

- Framework: Spring Boot 2.7.11
- Seguridad: JSON Web Tokens (JWT), BCrypt
- Base de datos: MySQL
- Librerías: Spring Security, JPA, Hibernate y Java Mail
- Manejo de dependencias: Maven
<br />
<h2>Cómo ejecutarlo:</h2>

Requerimientos: Primero necesitarás instalar lo siguiente:

- JDK 11+
- MySQL workbench

<p>Además, debes configurar las siguientes variables de entorno en tu sistema o en el archivo <code>application.yml</code>:</p>

- <code>DB_USERNAME</code>: nombre de usuario de la base de datos.
- <code>DB_PASSWORD</code>: contraseña del usuario de la base de datos.
- <code>ACCESS_SECRET</code>: clave secreta de más de 256 bits para generar y verificar los tokens de acceso.
- <code>ACCESS_EXPIRATION</code>: tiempo de expiración en horas del token de acceso.
- <code>CONFIRM_SECRET</code>: clave secreta de más de 256 bits para generar el token de confirmación.
- <code>CONFIRM_EXPIRATION</code>: tiempo de expiración en horas del token de confirmación.

Una vez cumplidos estos pasos, puedes clonar el proyecto y ejecutarlo en tu IDE preferido.

