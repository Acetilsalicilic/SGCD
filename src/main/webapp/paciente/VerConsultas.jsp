<!DOCTYPE html>
<%
    if (!Auth.Authorize.authPermission(session, "paciente")) {
        response.sendRedirect("/");
    }
%>
<html lang="en">
    <head>
        <link rel="stylesheet" href="/estilos/navbar.css" />
        <link rel="stylesheet" href="/estilos/paciente/consultas.css" />
        <link rel="stylesheet" href="/estilos/paciente/citas.css" />
        <script src="/paciente/js-scripts/consultas.js"></script>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Ver consultas</title>
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
                    <a href="/paciente/citas" class="nav-link"
                        >Ver citas</a
                    >
                </div>
                <div class="nav-element">
                    <a href="/paciente/consultas" class="nav-link"
                        >Ver consultas</a
                    >
                </div>
            </div>
        </div>

        <div class="main-container">
            <div class="inner-container">
                <h1 id="title">Ver consultas</h1>

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
