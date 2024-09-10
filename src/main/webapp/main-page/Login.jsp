<%-- 
    Document   : Login
    Created on : 9 sep 2024, 10:12:38 a.m.
    Author     : W10
--%>

<%@page import="DAO.EntityDAOPool"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            #form {
                display: flex;
                flex-direction: column;
            }
        </style>
    </head>
    <body>
        <h1>Inicio de sesión</h1>

        <div class="form-div">
            <form action="/api/auth" method="post" id="form">
                <label for="user">Usuario</label>
                <input type="text" name="user" id="user-input">
                <label for="text">Contrasena</label>
                <input type="text" name="password" id="password-input">
                <button type="submit">Iniciar sesion</button>
            </form>
        </div>
    </body>
</html>
