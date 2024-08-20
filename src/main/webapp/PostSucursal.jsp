<%-- 
    Document   : PostSucursal
    Created on : 19 ago 2024, 9:23:22 p.m.
    Author     : W10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear sucursal</title>
        <script>
            const setSend = () => {
                const $idInput = document.getElementById("id-input")
                const $nombreInput = document.getElementById("nombre-input")
                const $sendButton = document.getElementById("send-button")
                
                $sendButton.addEventListener('click', () => {
                    const ID = $idInput.value
                    const nombre = $nombreInput.value
                    
                    const data = {
                            ID,
                            nombre,
                            lugar: null
                        }
                    console.log('Posting')
                    console.log(data)

                    fetch('/api/sucursal', {
                        method: "post",
                        body: JSON.stringify(data)
                    })
                })
            }
        </script>
    </head>
    <body>
        <h1>Crear sucursal</h1>
        <div>
            <label for="id-input">ID</label>
            <input type="text" id="id-input" name="id">
            
            <label for="nombre-input">Nombre</label>
            <input type="text" id="nombre-input" name="nombre">
            
            <input type="button" value="Crear" id="send-button">
        </div>
        <script>
            setSend()
        </script>
    </body>
</html>
