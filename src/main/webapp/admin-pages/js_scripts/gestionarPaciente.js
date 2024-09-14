var floatingShown = false;
const availableUserTypes = ["admin", "paciente", "medico"];

//------------------------BUTTON FUNCTIONALITIES-------------
const showEditar = (el) => {
    if (floatingShown) return;

    floatingShown = true;

    let editarString;
    const id = el.getAttribute("data-id");

    fetch("/admin-pages/fragments/floatingEditPaciente.html")
        .then((response) => response.text())
        .then((text) => {
            editarString = text;

            const $floating = document.createElement("div");
            $floating.setAttribute("class", "editar-floating");
            $floating.setAttribute("id", "editar-floating");
            $floating.innerHTML = editarString;

            document.body.appendChild($floating);
            const $editarButton = $floating.querySelector(
                "#editar-enviar-button"
            );

            $editarButton.setAttribute("data-id", id);
            $editarButton.addEventListener("click", editarButton);
            floatingEditSetValues($floating, id);
        });
};

const hideEditar = (el) => {
    if (!floatingShown) return;
    floatingShown = false;

    const $floating = document.getElementById("editar-floating");
    document.body.removeChild($floating);
};

const showCrear = (el) => {
    console.log("show-crear");

    if (floatingShown) return;
    floatingShown = true;

    fetch("/admin-pages/fragments/floatingCrearPaciente.html")
        .then((response) => response.text())
        .then((string) => {
            const $crear = document.createElement("div");
            $crear.setAttribute("class", "crear-floating");
            $crear.setAttribute("id", "crear-floating");

            $crear.innerHTML = string;

            document.body.appendChild($crear);
        });
};

const hideCrear = (el) => {
    console.log("hide-crear");

    if (!floatingShown) return;
    floatingShown = false;

    const $crear = document.getElementById("crear-floating");
    document.body.removeChild($crear);
};

const searchButton = (el) => {
    const $radio = document.querySelector("input[name=tipo]:checked");
    const type = $radio.value;

    const $query = document.getElementById("query-input");
    const query = $query.value;

    const $list = document.querySelector(".pacientes-list");
    $list.innerHTML = "";

    getPacientes(type, query).then((json) => {
        const convertedPacientes = convertPacientes(json);
        if (convertedPacientes.length > 0)
            convertedPacientes.forEach(addPacienteElement);
        else showNoPacienteFound(type, query);
    });
};

const loadInfo = () => {
    console.log("loading info");

    const $list = document.querySelector(".pacientes-list");
    $list.innerHTML = "";

    getPacientes("name", "").then((json) => {
        const convertedPacientes = convertPacientes(json);
        if (convertedPacientes.length > 0)
            convertedPacientes.forEach(addPacienteElement);
        else showNoPacientesAvailable();
    });
};

const editarButton = () => {
    console.log("going to edit...");

    const $floating = document.querySelector(".editar-floating");
    const id = $floating.querySelector("#editar-enviar-button").value;

    floatingEditGetValues().then((paciente) => {
        fetch("/api/pacientes", {
            method: "put",
            body: JSON.stringify(paciente),
        })
            .then((response) => response.json())
            .then((res) => {
                console.log(res);

                if (res.error) {
                    console.error(
                        "something went wrong while trying to edit paciente"
                    );
                    console.error(res.error);
                    alert("Algo salio mal");
                }
                if (res.status) {
                    console.log("Success updating paciente with id " + id);
                    alert("El paciente se edito correctamente");
                    hideEditar();
                    loadInfo();
                }
            })
            .catch((error) => {
                console.error(
                    "something went wrong while trying to edit paciente in promise"
                );
                console.error(error);
                alert("Algo salio mal");
            });
    });
};

const eliminarButton = (el) => {
    console.log("going to delete some folks...");
    const paciente_id = el.getAttribute("data-id");
    const rs = confirm(
        `Estas seguro de querer eliminar al paciente con id ${paciente_id}?`
    );

    if (!rs) return;

    fetch("/api/pacientes", {
        method: "delete",
        body: JSON.stringify({ paciente_id }),
    })
        .then((response) => response.json())
        .then((json) => {
            console.log(json);
            if (json.error) {
                alert("Algo salio mal");
            }
            if (json.status) {
                alert("El paciente se elimino con exito");
                loadInfo();
            }
        });
};

const crearButton = (el) => {
    const $crear = document.body.querySelector(".crear-floating");

    data = {
        nombre: $crear.querySelector("#crear-nombre").value,
        apellidos: $crear.querySelector("#crear-apellidos").value,
        telefono: $crear.querySelector("#crear-telefono").value,
        direccion: $crear.querySelector("#crear-direccion").value,
        tipo_usuario: $crear.querySelector("#crear-tipo").value,
        usuario: $crear.querySelector("#crear-usuario").value,
        contrasena: $crear.querySelector("#crear-contrasena").value,
    };

    for (key in data) {
        if (!data[key]) {
            alert(`El campo ${key} es invalido!`);
            return;
        }
    }

    if (!availableUserTypes.includes(data.tipo_usuario)) {
        alert(`El tipo de usuario ${data.tipo_usuario} no existe!`);
        return;
    }

    postPaciente(data)
        .then((response) => response.json())
        .then((json) => {
            console.log(json);
            if (json.error) {
                console.error(json);
                alert("Algo salio mal");
            }
            if (json.status) {
                alert("El paciente se creo con exito");
                hideCrear();
                loadInfo();
            }
        })
        .catch((error) => {
            console.error(error);
            alert("Algo salio mal");
        });
};

//--------------DATA FETCHING--------------

const getPacientes = async (type, query) => {
    const response = await fetch(`/api/pacientes?type=${type}&query=${query}`);
    const json = await response.json();
    return json;
};

const postPaciente = async (paciente) => {
    const response = await fetch("/api/pacientes", {
        method: "post",
        body: JSON.stringify(paciente),
    });

    return response;
};

const getPacienteById = async (id) => {
    return await getPacientes("id", id);
};

const convertPacientes = (json) => {
    const listaPacientesJson = json.pacientes;

    const listaPacientes = [];

    for (index in listaPacientesJson) {
        const paciente = listaPacientesJson[index];

        listaPacientes.push({
            id: paciente.id_paciente,
            nombre: paciente.nombre,
            apellidos: paciente.apellidos,
            telefono: paciente.telefono,
            direccion: paciente.direccion,
            usuario: paciente.usuario.nombre_usuario,
            contrasena: paciente.usuario.contrasena,
        });
    }

    return listaPacientes;
};

//-----------------DOM EDITING-----------------
const floatingEditSetValues = async ($floating, id) => {
    const json = await getPacienteById(id);
    const paciente = convertPacientes(json)[0];

    console.log(json);

    console.log(paciente);

    $floating.querySelector("#editar-nombre").value = paciente.nombre;
    $floating.querySelector("#editar-apellidos").value = paciente.apellidos;
    $floating.querySelector("#editar-telefono").value = paciente.telefono;
    $floating.querySelector("#editar-direccion").value = paciente.direccion;
    $floating.querySelector("#editar-usuario").value = paciente.usuario;
    $floating.querySelector("#editar-contrasena").value = paciente.contrasena;
};

const floatingEditGetValues = async () => {
    const $floating = document.querySelector(".editar-floating");
    const id = $floating
        .querySelector("#editar-enviar-button")
        .getAttribute("data-id");

    return (paciente = {
        nombre: $floating.querySelector("#editar-nombre").value,
        apellidos: $floating.querySelector("#editar-apellidos").value,
        telefono: $floating.querySelector("#editar-telefono").value,
        direccion: $floating.querySelector("#editar-direccion").value,
        usuario: $floating.querySelector("#editar-usuario").value,
        contrasena: $floating.querySelector("#editar-contrasena").value,
        id,
    });
};

const createPacienteElement = ({
    id,
    nombre,
    apellidos,
    telefono,
    direccion,
    usuario,
    contrasena,
}) => {
    const $pacienteElement = document.createElement("div");
    $pacienteElement.setAttribute("class", "pacientes-element");
    $pacienteElement.innerHTML = `
    <div class="pacientes-col-wrapper">
    <div class="pacientes-label-col">
        <label class="data-label" for="nombre">Nombre</label>
        <label class="data-label" for="apellidos">Apellidos</label>
        <label class="data-label" for="telefono">Telefono</label>
        <label class="data-label" for="direccion">Direccion</label>
        <label class="data-label" for="usuario">Usuario</label>
        <label class="data-label" for="contrasena">Contrasena</label>
        <label class="data-label" for="id">ID</label>
    </div>

    <div class="pacientes-data-col">
        <p class="data-display" id="apellidos">${nombre}</p>
        <p class="data-display" id="nombre">${apellidos}</p>
        <p class="data-display" id="telefono">${telefono}</p>
        <p class="data-display" id="direccion">${direccion}</p>
        <p class="data-display" id="usuario">${usuario}</p>
        <p class="data-display" id="contrasena">${contrasena}</p>
        <p class="data-display" id="id">${id}</p>
    </div>
</div>

<div class="pacientes-buttons-container">
    <button 
        type="button" 
        class="pacientes-button" 
        onclick="eliminarButton(this)" 
        data-id="${id}">Eliminar</button>
    <button
        type="button"
        class="pacientes-button"
        onclick="showEditar(this)"
        data-id="${id}"
    >
        Editar
    </button>
</div>
    `;

    return $pacienteElement;
};

const addPacienteElement = (paciente) => {
    const $listaUsuarios = document.querySelector(".pacientes-list");
    $listaUsuarios.appendChild(createPacienteElement(paciente));
};

const showNoPacienteFound = (type, query) => {
    console.log("no pacientes found");

    const $warn = document.createElement("div");
    $warn.setAttribute("class", "pacientes-element");
    $warn.innerHTML = `<p>No se encontro ningun paciente con el ${
        type == "name" ? "nombre" : "id"
    } "${query}"!`;
    const $list = document.querySelector(".pacientes-list");
    $list.appendChild($warn);
};

const showNoPacientesAvailable = () => {
    console.log("no pacientes found");

    const $warn = document.createElement("div");
    $warn.setAttribute("class", "pacientes-element");
    $warn.innerHTML = "No hay pacientes para mostrar";
    const $list = document.querySelector(".pacientes-list");
    $list.appendChild($warn);
};
