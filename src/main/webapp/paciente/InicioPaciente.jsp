<%@page import="Auth.Authorize"%>
<!DOCTYPE html>
<% //-----------AUTH------------- 
if (!Authorize.authPermission(session,
"paciente")) { response.sendRedirect("/"); } 
%>
<html>
    <head>
        <link rel="stylesheet" href="/estilos/navbar.css" />
        <link rel="stylesheet" href="/estilos/paciente/inicioPaciente.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Inicio paciente</title>
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
                    <a href="/paciente/citas" class="nav-link">Ver citas</a>
                </div>
            </div>
        </div>

        <div class="page-container">
            <div class="center-element">
                <h1 class="title">Inicio paciente</h1>
                <p id="welcome-parag">Bienvenido, <%= session.getAttribute("username") %>.</p>
            </div>
        </div>
    </body>
</html>
