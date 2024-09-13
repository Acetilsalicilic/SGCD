var floatingShown = false;

//------------------------BUTTON FUNCTIONALITIES-------------
const showEditar = (el) => {
    if (floatingShown) return;

    floatingShown = true;

    let editarString;

    fetch("/admin-pages/fragments/floatingEditPaciente.html")
        .then((response) => response.text())
        .then((text) => {
            editarString = text;

            const $floating = document.createElement("div");
            $floating.setAttribute("class", "editar-floating");
            $floating.setAttribute("id", "editar-floating");
            $floating.innerHTML = editarString;

            document.body.appendChild($floating);
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

//--------------DATA FETCHING--------------

const getPacientes = async (type, query) => {
    const response = await fetch(`/api/pacientes?type=${type}&query=${query}`);
    const json = await response.json();
    console.log(json);
    return json;
};

const convertPacientes = (json) => {
    const listaPacientesJson = json.pacientes;
    console.log(listaPacientesJson);
    console.log(typeof listaPacientesJson);

    const listaPacientes = [];

    for (index in listaPacientesJson) {
        const paciente = listaPacientesJson[index];

        console.log("paciente: ");
        console.log(paciente);

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
    <button type="button" class="pacientes-button">Eliminar</button>
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
