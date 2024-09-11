<%-- 
    Document   : GestionarPacientes
    Created on : 10 sep 2024, 9:05:15 p.m.
    Author     : W10
--%>

<%@page import="util.Utils"%>
<%@page import="Records.Paciente"%>
<%@page import="java.util.stream.Stream"%>
<%@page import="Records.Usuario"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.EntityDAOPool"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestionar pacientes</title>
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

            .crear-paciente {
                display: flex;
                flex-direction: column;
            }

            .crear-paciente-container {
                display: flex;
                flex-direction: column;
            }
        </style>
        <%
            //                       AUTHORIZATION
            if (session == null || !session.getAttribute("auth").equals("admin")) {
                response.sendRedirect("/");
            }

            //                      CONFIRMATION
            if (request.getAttribute("create-status") != null) {
                out.print("<script>alert('Status de la creacion de paciente: " + request.getAttribute("create-status") + "')</script>");
            }
        %>
    </head>
    <body>
        <div class="all-container">

            <h1>Pacientes existentes</h1>
            <a href="/admin">Volver a inicio</a>
            <form action="/admin/pacientes" class="form">
                <label for="query">Introduce el parametro de busqueda</label>
                <%
                    out.println("<input type=\"text\" name=\"query\" id=\"query-input\" value=\"" + (request.getParameter("query") != null ? request.getParameter("query") : "") + "\">");
                %>
                <label>Tipo de busqueda</label>

                <label for="radio-1">Por nombre</label>
                <input type="radio" id="radio-1" name="tipo" value="nombre" checked="">

                <label for="radio-1">Por id</label>
                <input type="radio" id="radio-2" name="tipo" value="id">

                <button type="submit">Buscar</button>
            </form>
            <div class="list-container">
                <ul class="element-list">
                    <%
                        String query = request.getParameter("query");
                            String tipo = request.getParameter("tipo");

                                tipo = tipo == null ? "nombre" : tipo;

                            var dao = EntityDAOPool.instance().getPacienteDAO();
                        var usuarios = dao.getAll();

                        if (query != null) {
                                if (tipo.equals("nombre")) {
                                    usuarios = dao.getByNombre(query);
                                }
                                if (tipo.equals("id")) {
                                    if (Utils.isNumeric(query) && dao.getById(Integer.parseInt(query)) != null) {
                                        usuarios.clear();
                                        usuarios.add(dao.getById(Integer.parseInt(query)));
                                    } else {
                                        System.out.println("Query is not numeric!");
                                        usuarios = new ArrayList<Paciente>();
                                    }
                                }
                            }


                        if (usuarios.isEmpty()) {
                            out.println("<p>No se encontraron pacientes con el " + tipo + " \"" + query + "\"</p>");
                        } else {
                            for (var usuario : usuarios) {
                                out.println("<li class=\"usuario-element\">"
                                        + "<div class=\"usuario-row\"><p>Nombre de paciente:</p><p>" + usuario.nombre() + "</p></div>"
                                                + "<div class=\"usuario-row\"><p>Apellidos:</p><p>" + usuario.apellidos() + "</p></div>"
                                                        + "<div class=\"usuario-row\"><p>ID:</p><p>" + usuario.id() + "</p></div>"
                                        + "</li>");
                            }
                        }


                    %>
                    <li class="usuario-element crear-paciente">
                        <form action="/api/pacientes" method="post" class="crear-paciente">
                            <div class="crear-paciente-container">

                                <label for="nombre-input">Nombre del paciente</label>
                                <input type="text" name="nombre" id="nombre-input">
    
                                <label for="apellidos-input">Apellidos</label>
                                <input type="text" name="apellidos" id="appellidos-inpit">
    
                                <label for="telefono-input">Telefono</label>
                                <input type="text" name="telefono" id="telefono-input">
    
                                <label for="direccion-input">Direccion</label>
                                <input type="text" name="direccion" id="direccion-input">
    
                                <input type="submit" value="Crear paciente">
                                <input type="reset" value="Limpiar campos">
                            </div>
                        </form>
                    </li>
                </ul>
            </div>

        </div>
    </body>
</html>
