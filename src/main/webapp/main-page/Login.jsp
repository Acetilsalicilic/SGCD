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
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            EntityDAOPool.init("jdbc:mysql://localhost:3306/lab4", "root", "pass");
            var dao = EntityDAOPool.instance().getUsuarioDAO();
            var usuario = dao.getById(1);
        %>
        
        <p>
            <%= usuario.toString() %>
        </p>
    </body>
</html>
