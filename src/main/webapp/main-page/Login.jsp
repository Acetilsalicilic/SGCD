<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="../estilos/navbar.css" />
        <link rel="stylesheet" href="../estilos/main/login.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Iniciar sesion</title>
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
                    <a href="/" class="nav-link">Inicio</a>
                </div>
            </div>
        </div>

        <div class="main-container">
            <div class="form-div">
                <h1 class="form-title" id="form-title">Iniciar sesion</h1>

                <form action="/api/auth" method="post" id="form">
                    <div class="form-container">
                        <label for="user" class="form-label">Usuario</label>
                        <input
                            type="text"
                            name="user"
                            id="user-input"
                            class="form-input"
                            placeholder="Nombre de usuario"
                        />

                        <label for="text" class="form-label">Contrasena</label>
                        <input
                            type="password"
                            name="password"
                            id="password-input"
                            class="form-input"
                            placeholder="Contrasena"
                        />

                        <button
                            type="submit"
                            class="submit-button"
                            id="submit-button"
                        >
                            Iniciar sesion
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            var errorShown = false;

            const showError = () => {
                if (errorShown) return;

                errorShown = true;

                const $form = document.getElementById("form");
                const $form_div = $form.parentNode;

                const $error = document.createElement("p");
                $error.setAttribute("class", "error-message");
                $error.innerText = "El usuario o contrasena es incorrecto!";

                $form_div.insertBefore($error, $form);
            };

            const submitCredentials = () => {
                const $button = document.getElementById("submit-button");
                $button.addEventListener("click", (evt) => {
                    evt.preventDefault();

                    const $username_input =
                        document.getElementById("user-input");
                    const $password_input =
                        document.getElementById("password-input");

                    const username = $username_input.value;
                    const password = $password_input.value;

                    const json = JSON.stringify({
                        username,
                        password,
                    });

                    console.log(json);

                    fetch("/api/auth", {
                        headers: {
                            "Content-Type": "application/json",
                        },
                        method: "post",
                        body: json,
                        credentials: "include",
                    })
                        .then((response) => response.json())
                        .then((json) => {
                            console.log(json);
                            console.log(json.auth_correct);

                            if (json.auth_error) {
                                showError();
                            }

                            if (json.auth_correct) {
                                const newUrl = "/" + json.auth_correct;
                                console.log(newUrl);

                                window.location.replace(newUrl);
                            }
                        })
                        .catch((error) => {
                            console.error("Error while submiting credentials");
                            console.error(error);
                        });
                });
            };

            submitCredentials();
        </script>
    </body>
</html>
