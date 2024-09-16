<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="/estilos/navbar.css" />
        <link rel="stylesheet" href="/estilos/medico/consultas.css" />
        <link
            rel="stylesheet"
            href="/estilos/paciente/floating/floatingCrearCita.css"
        />
        <script src="/medico/js-scripts/consultas.js"></script>
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
                    <a href="/medico" class="nav-link">Inicio medico</a>
                </div>
                <div class="nav-element">
                    <a href="#" class="nav-link" onclick="showCrear()"
                        >Agendar consulta</a
                    >
                </div>
                <div class="nav-element">
                    <a href="/medico/consultas" class="nav-link"
                        >Ver consultas</a
                    >
                </div>
            </div>
        </div>

        <div class="main-container">
            <div class="inner-container">
                <h1 id="title">Gestionar citas</h1>

                <div class="consultas-table">
                    <div class="consultas-list"></div>
                </div>
            </div>
        </div>
        <script>
            loadInfo();
        </script>
    </body>
</html>
