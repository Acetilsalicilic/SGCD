<!DOCTYPE html>
<html lang="en">
    <head>
        <% if (!Auth.Authorize.authPermission(session, "admin")) {
        response.sendRedirect("/"); } %>
        <link rel="shortcut icon" href="../favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="../estilos/navbar.css" />
        <link rel="stylesheet" href="../estilos/admin/gestionarMedicos.css" />
        <link
            rel="stylesheet"
            href="../estilos/admin/floating/floatingCrearMedico.css"
        />
        <script src="/admin-pages/js_scripts/gestionarMedico.js"></script>
        <link
            rel="stylesheet"
            href="../estilos/admin/floating/floatingCrearMedico.css"
        />
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Gestionar medicos</title>
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
                    <a href="#" class="nav-link" onclick="showCrear()"
                        >Crear medico</a
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
                <h1 id="title">Gestionar medicos</h1>

                <div class="search-box">
                    <div class="search-row">
                        <p>Buscar medico</p>
                    </div>
                    <div class="search-row">
                        <input type="text" name="query" id="query-input" />
                        <button
                            type="button"
                            id="buscar-button"
                            onclick="searchButton()"
                        >
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
                                value="name"
                                checked
                            />
                        </div>
                        <div class="radio-element">
                            <label for="radio-especialidad"
                                >Por especialidad</label
                            >
                            <input
                                type="radio"
                                name="tipo"
                                id="radio-especialidad"
                                class="radio-button"
                                value="specialty"
                            />
                        </div>
                    </div>
                </div>

                <div class="medicos-table">
                    <div class="medicos-list"></div>
                </div>
            </div>
        </div>
        <script>
            loadInfo();
        </script>
    </body>
</html>
