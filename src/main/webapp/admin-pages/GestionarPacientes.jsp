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
            <a href="/admin">Volver a inicio</a>
            <form action="/admin/pacientes" class="form">
                <label for="query">Introduce el parametro de busqueda</label>
                <input type="text" name="query" id="query-input">
                <label>Tipo de busqueda</label>

                <label for="radio-1">Por nombre</label>
                <input type="radio" id="radio-1" name="tipo" value="nombre">

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
                                Stream<Paciente> filteredPacientes = null;

                                if (tipo.equals("nombre")) {
                                    filteredPacientes = usuarios.stream().filter(usr -> usr.nombre().toLowerCase().contains(query.toLowerCase()));
                                }
                                if (tipo.equals("id")) {
                                    if (Utils.isNumeric(query))
                                            filteredPacientes = usuarios.stream().filter(pac -> pac.id().intValue() == Integer.parseInt(query));
                                        else
                                            filteredPacientes = null;

                                }

    if (filteredPacientes == null)
        usuarios = new ArrayList<>();
    else
        usuarios = filteredPacientes.collect(Collectors.toCollection(ArrayList::new));
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
                </ul>
            </div>

        </div>
    </body>
</html>
