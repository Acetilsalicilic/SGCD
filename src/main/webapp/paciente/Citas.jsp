<%@page import="Auth.Authorize"%>
<!DOCTYPE html>
<% //-----------AUTH------------- 
if (!Authorize.authPermission(session,"paciente")) { response.sendRedirect("/"); }
%>
<html lang="en">
    <head>
        <link rel="stylesheet" href="/estilos/navbar.css" />
        <link rel="stylesheet" href="/estilos/paciente/citas.css" />
        <link
            rel="stylesheet"
            href="/estilos/paciente/floating/floatingCrearCita.css"
        />
        <script src="/paciente/js-scripts/citas.js"></script>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Ver citas</title>
    </head>
    <body>
        <div class="navbar">
            <div class="nav-logo">
                <a href="/">
                    <img src="../img/logo-sgcd.jpg" alt="" id="nav-logo-img" />
                </a>
            </div>

            <div class="nav-element-container">
                <div class="nav-element">
                    <a href="/paciente" class="nav-link">Inicio paciente</a>
                </div>
                <div class="nav-element">
                    <a href="#" class="nav-link" onclick="showCrear()"
                        >Agendar cita</a
                    >
                </div>
                <div class="nav-element">
                    <a href="/paciente/citas" class="nav-link">Ver citas</a>
                </div>
            </div>
        </div>

        <div class="main-container">
            <div class="inner-container">
                <h1 id="title">Gestionar citas</h1>

                <div class="citas-table">
                    <div class="citas-list"></div>
                </div>
            </div>
        </div>
        <script>
            loadInfo();
        </script>
    </body>
</html>
