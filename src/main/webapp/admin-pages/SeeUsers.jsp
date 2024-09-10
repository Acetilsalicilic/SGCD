<%-- 
    Document   : SeeUsers
    Created on : 10 sep 2024, 9:30:24 a.m.
    Author     : W10
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="DAO.EntityDAOPool"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver usuarios</title>
        <style>
            body {
                display: flex;
                justify-content: center;
            }

            .all-container {
                display: flex;
                flex-direction: column;
                width: 40%;
                align-items: center;
                align-self: center;
            }

            .form {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .list-container {
                width: 100%;
            }

            .usuario-row {
                display: flex;
                flex-direction: row;
                justify-content: space-between;
            }
        </style>
        <%
            if (session == null || !session.getAttribute("auth").equals("admin")) {
                    response.sendRedirect("/");
                }
        %>
    </head>
    <body>
        <div class="all-container">

            <h1>Usuarios existentes</h1>
            <a href="/">Volver a inicio</a>
            <form action="/usuarios" class="form">
                <label for="query">Introduce el parametro de busqueda</label>
                <input type="text" name="query" id="query-input">
                <button type="submit">Buscar</button>
            </form>
            <div class="list-container">
                <ul class="element-list">
                    <%
                        String query = request.getParameter("query");
                        var dao = EntityDAOPool.instance().getUsuarioDAO();
                            var usuarios = dao.getAll();
    
                        if (query != null) {
                                var filteredUsuarios = usuarios.stream().filter(usr -> usr.nombre_usuario().toLowerCase().contains(query.toLowerCase()));
                                usuarios = filteredUsuarios.collect(Collectors.toCollection(ArrayList::new));
                            }
    
                    if (usuarios.isEmpty()) {
                        out.println("<p>No se encontraron usuarios con el nombre " + query + "</p>");
                    } else {
                        for (var usuario : usuarios) {
                            out.println("<li class=\"usuario-element\">"
                                    + "<div class=\"usuario-row\"><p>Nombre de usuario:</p><p>" + usuario.nombre_usuario() + "</p></div>"
                                    + "<div class=\"usuario-row\"><p>Tipo de usuario:</p><p>" + usuario.tipo() + "</p></div>"
                                    + "</li>");
                        }
                    }
    
    
                    %>
                </ul>
            </div>

        </div>
    </body>
</html>
