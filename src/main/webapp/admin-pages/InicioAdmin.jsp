<%-- 
    Document   : InicioAdmin
    Created on : 10 sep 2024, 8:50:09 p.m.
    Author     : W10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio administrador</title>
        <%
            if (session.getAttribute("auth") == null || !session.getAttribute("auth").equals("admin")) {
                response.sendRedirect("/");
            }
        %>
    </head>
    <body>
        <h1>Inicio admin</h1>
        <a href="/">Volver a inicio</a>
        <a href="/admin/usuarios">Ver usuarios</a>
        <a href="/admin/pacientes">Ver pacientes</a>
    </body>
</html>
