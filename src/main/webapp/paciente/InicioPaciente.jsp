<%-- 
    Document   : InicioPaciente
    Created on : 10 sep 2024, 12:37:14 p.m.
    Author     : W10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%
            if (session.getAttribute("auth") == null || !session.getAttribute("auth").equals("paciente")) {
                response.sendRedirect("/");
    }

        %>
    </head>
    <body>
        <h1>Paciente</h1>
        <input type="datetime-local" name="" id="" step="1800" min="30">
    </body>
</html>
