<%@page import="Auth.Authorize"%>
<!DOCTYPE html>
<% //-----------AUTH------------- 
if (!Authorize.authPermission(session,
"admin")) { response.sendRedirect("/"); } 
%>
<html>
    <head>
        <link rel="stylesheet" href="../estilos/navbar.css" />
        <link rel="stylesheet" href="../estilos/admin/inicioAdmin.css" />
        <link rel="shortcut icon" href="/favicon.png" type="image/x-icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Inicio administrador</title>
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
    </body>

    <div class="page-container">
        <div class="center-element">
            <h1 class="title">Inicio administrador</h1>
            <p id="welcome-parag">Bienvenido, <%= session.getAttribute("username") %>.</p>
        </div>
    </div>
</html>
