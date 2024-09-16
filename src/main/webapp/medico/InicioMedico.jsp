<%@page import="DAO.EntityDAOPool"%>
<%@page import="Auth.Authorize"%>
<!DOCTYPE html>
<% //-----------AUTH------------- 
if (!Authorize.authPermission(session,
"medico")) { response.sendRedirect("/"); } 
%>
<html>
    <head>
        <link rel="stylesheet" href="/estilos/navbar.css" />
        <link rel="stylesheet" href="/estilos/medico/inicioMedico.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Inicio medico</title>
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
                    <a href="/medico/consultas" class="nav-link">Ver consultas</a>
                </div>
            </div>
        </div>

        <div class="page-container">
            <div class="center-element">
                <h1 class="title">Inicio medico</h1>
                <p id="welcome-parag">Bienvenido, <%= session.getAttribute("username") %>.</p>
            </div>
        </div>
    </body>
</html>
