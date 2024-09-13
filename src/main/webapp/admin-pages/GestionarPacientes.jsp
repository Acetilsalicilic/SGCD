<%@page import="DAO.EntityDAOPool"%>
<%@page import="Auth.Authorize"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%
            if (!Authorize.authPermission(session, "admin")) {
                response.sendRedirect("/");
            }
        %>
        <link rel="stylesheet" href="../estilos/navbar.css" />
        <link rel="stylesheet" href="../estilos/admin/gestionarPacientes.css" />
        <link
            rel="stylesheet"
            href="../estilos/admin/floating/floatingEditarPaciente.css"
        />
        <link
            rel="stylesheet"
            href="../estilos/admin/floating/floatingCrearPaciente.css"
        />
        <script src="./js_scripts/gestionarPaciente.js"></script>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Gestionar pacientes</title>
    </head>
    <body>
        <div class="navbar">
            <div class="nav-logo">
                <a href="/">
                    <img src="../img/logo-sgcd.jpg" alt="" id="nav-logo-img" />
                </a>
            </div>

            <div class="nav-element-container">
                <div class="nav-element create-element-nav">
                    <a href="#" class="nav-link" onclick="showCrear(this)"
                        >Crear paciente</a
                    >
                </div>
                <div class="nav-element">
                    <a href="/admin" class="nav-link">Inicio admin</a>
                </div>
                <div class="nav-element">
                    <a href="/admin/pacientes" class="nav-link"
                        >Ver pacientes</a
                    >
                </div>
                <div class="nav-element">
                    <a href="/admin/medicos" class="nav-link">Ver medicos</a>
                </div>
            </div>
        </div>

        <div class="main-container">
            <div class="inner-container">
                <h1 id="title">Gestionar pacientes</h1>

                <div class="search-box">
                    <form id="search-form">
                        <div class="search-row">
                            <p>Buscar paciente</p>
                        </div>

                        <div class="search-row">
                            <input type="text" name="query" id="query-input" />
                            <button type="button" id="buscar-button">
                                Buscar
                            </button>
                        </div>

                        <div class="search-row radio-container">
                            <div class="radio-element">
                                <label for="radio-nombre">Por nombre</label>
                                <input
                                    type="radio"
                                    name="tipo"
                                    id="radio-nombre"
                                    class="radio-button"
                                    checked
                                />
                            </div>

                            <div class="radio-element">
                                <label for="radio-id">Por id</label>
                                <input
                                    type="radio"
                                    name="tipo"
                                    id="radio-id"
                                    class="radio-button"
                                />
                            </div>
                        </div>
                    </form>
                </div>

                <div class="pacientes-table">
                    <div class="pacientes-list">
                        <!-------------------LIST ELEMENT---------->
                        <div class="pacientes-element">
                            <div class="pacientes-col-wrapper">
                                <div class="pacientes-label-col">
                                    <label class="data-label" for="nombre"
                                        >Nombre</label
                                    >
                                    <label class="data-label" for="apellidos"
                                        >Apellidos</label
                                    >
                                    <label class="data-label" for="telefono"
                                        >Telefono</label
                                    >
                                    <label class="data-label" for="direccion"
                                        >Direccion</label
                                    >
                                    <label class="data-label" for="usuario"
                                        >Usuario</label
                                    >
                                    <label class="data-label" for="contrasena"
                                        >Contrasena</label
                                    >
                                </div>

                                <div class="pacientes-data-col">
                                    <p class="data-display" id="apellidos">
                                        Test
                                    </p>
                                    <p class="data-display" id="nombre">Test</p>
                                    <p class="data-display" id="telefono">
                                        667
                                    </p>
                                    <p class="data-display" id="direccion">
                                        Aca
                                    </p>
                                    <p class="data-display" id="usuario">
                                        Usuario
                                    </p>
                                    <p class="data-display" id="contrasena">
                                        Contrasena
                                    </p>
                                </div>
                            </div>

                            <div class="pacientes-buttons-container">
                                <button type="button" class="pacientes-button">
                                    Eliminar
                                </button>
                                <button
                                    type="button"
                                    class="pacientes-button"
                                    onclick="showEditar(this)"
                                >
                                    Editar
                                </button>
                            </div>
                        </div>
                        <!--END OF ELEMENT-->
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
