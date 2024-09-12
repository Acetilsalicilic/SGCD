<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <style>
            body {
                display: flex;
                flex-direction: column;
            }
            .main-div {
                display: flex;
                flex-direction: column;
            }
        </style>

        <script>
            const pingServer = () => {
                console.log("Calling server");

                fetch("/api")
                    .then((response) => response.json())
                    .then((json) => {
                        if (json.api_status == "ok") {
                            console.log(json);
                        } else {
                            console.error("Error in response:");
                            console.error(json);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            };

            pingServer();
        </script>
    </head>
    <body>
        <h1>Inicio</h1>

        <div class="main-div">
            <a href="/auth">Login</a>
            <a href="/sucursal">Post sucursal</a>
            <a href="/SessionTestPage.html">Test session</a>
        </div>
    </body>
</html>
