package com.example.confirmemailregistration.utils;

public class EmailTemplates {

    public static String getConfirmationEmailTemplate(String token) {
        StringBuilder builder = new StringBuilder();

        builder
                .append("<div style=\"width:100%;height:100%;display:flex;flex-direction:column;justify-content:center;align-items:center;\">")
                .append("<div style=\"width:100%;display:flex;justify-content:center;\">")
                .append("<h3 style=\"font-size: 32px;\">Bienvenido!</h3></div>")
                .append("<p style=\"font-size: 24px;\">Gracias por registrarte en nuestro sitio web. Para asegurarnos de que el ")
                .append("correo electrónico proporcionado es válido, necesitamos que confirmes tu dirección de correo electrónico haciendo clic ")
                .append("en el siguiente enlace:</p>")
                .append("<div style=\"width:100%;display:flex;justify-content:center\">")
                .append("<a style=\"border:1px solid black;background:none;font-size: 24px;padding:10px 14px;border-radius")
                .append(":4px;text-decoration:none;color:black;\" href=\"http://localhost:3000/confirmation/")
                .append(token)
                .append("\" target=\"_blank\">Confirmar ")
                .append("cuenta!</a> </div></div>");
        return builder.toString();
    }
}
