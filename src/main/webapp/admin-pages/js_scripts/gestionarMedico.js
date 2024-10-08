//--------------------ON PAGE LOAD

function loadInfo() {
    getAllMedicos().then((info) => {
        console.log("loading info");
        const data = convertMedicos(info);
        document.body.querySelector(".medicos-list").innerHTML = "";
        if (data.length == 0) {
            showListMessage("No hay medicos para mostrar!");
        } else {
            data.forEach((medico) => addMedicoElement(medico));
        }
    });
}

//-----------------BUTTON FUNCTIONALITIES

function searchButton() {
    const $radio = document.querySelector("input[name=tipo]:checked");
    const type = $radio.value;

    const $query = document.getElementById("query-input");
    const query = $query.value;

    const $list = document.querySelector(".medicos-list");
    $list.innerHTML = "";

    getMedicosQuery(type, query).then((data) => {
        const medicos = convertMedicos(data);
        if (medicos.length == 0) {
            showListMessage(
                `No se encontraron medicos con ${
                    type == "name" ? "el nombre" : "la especialidad"
                } ${query}!`
            );
        } else {
            medicos.forEach((medico) => {
                addMedicoElement(medico);
            });
        }
    });
}

function createButton() {
    const $floating = document.body.querySelector(".crear-floating");

    let medico;

    try {
        medico = {
            nombre: $floating.querySelector("#crear-nombre").value,
            apellidos: $floating.querySelector("#crear-apellidos").value,
            especialidad: $floating.querySelector("#crear-especialidad").value,
            desc_tipo: $floating.querySelector("#crear-tipo").value,
            nombre_usuario: $floating.querySelector("#crear-usuario").value,
            contrasena: $floating.querySelector("#crear-contrasena").value,
        };
    } catch (error) {
        alert("Hubo un error al recolectar los datos!");
        console.error(error);
        return;
    }

    postMedico(medico)
        .then((json) => {
            if (json.status) {
                alert("El medico se creo correctamente");
                loadInfo();
                hideCrear();
            } else if (json.error) {
                alert("Algo salio mal");
                console.error(json);
                loadInfo();
            }
        })
        .catch((error) => {
            alert("Algo salio mal");
            console.error(error);
        });
}

function deleteButton(el) {
    const id = el.getAttribute("data-id");
    const nombre = el.getAttribute("data-name");

    const rs = confirm(
        `Estas seguro de eliminar al medico con nombre ${nombre}?`
    );

    if (!rs) return;
    console.log("Let's delete some folks");

    deleteMedico(id).then((json) => {
        if (json.status) {
            alert("El medico se elimino con exito");
            loadInfo();
        } else {
            alert("Hubo un error");
            loadInfo();
            console.error(json);
        }
    });
}

function editarButton(el) {
    console.log("Going to edit some folks");

    const $floating = document.body.querySelector(".editar-floating");

    const prevMedico = JSON.parse(
        el.parentElement.parentElement.getAttribute("data-medico")
    );
    console.log(prevMedico);

    try {
        medico = {
            nombre: $floating.querySelector("#editar-nombre").value,
            apellidos: $floating.querySelector("#editar-apellidos").value,
            especialidad: $floating.querySelector("#editar-especialidad").value,
            nombre_usuario: $floating.querySelector("#editar-usuario").value,
            contrasena: $floating.querySelector("#editar-contrasena").value,
            id_tipo: prevMedico.tipoUsuario.id_tipo,
            id_usuario: prevMedico.id_usuario,
            id: prevMedico.id,
        };
    } catch (error) {
        alert("Hubo un error al recolectar los datos!");
        console.error(error);
        return;
    }

    patchMedico(medico).then((json) => {
        if (json.status) {
            alert("El medico se actualizo con exito");
        } else {
            alert("Algo salio mal");
            console.error(json);
        }
        hideEditar();
        loadInfo();
    });
}

//-----------------DATA MANIPULATION
function convertMedicos(raw) {
    const medicos = [];

    for (key in raw) {
        const {
            id_medico,
            nombre_medico,
            apellidos_medico,
            usuario,
            especialidad,
        } = raw[key];

        const medico = {
            id: id_medico,
            nombre: nombre_medico,
            apellidos: apellidos_medico,
            ...usuario,
            ...especialidad,
        };
        medicos.push(medico);
    }

    return medicos;
}

//------------------SHOW FLOATING MENUS
var floatingShown = false;

function showCrear() {
    if (floatingShown) return;
    floatingShown = true;
    fetchFragment("/admin-pages/fragments/floatingCrearMedico.html").then(
        (html) => {
            const $element = document.createElement("div");
            $element.setAttribute("class", "crear-floating");
            $element.innerHTML = html;
            document.body.appendChild($element);
        }
    );
}

function hideCrear() {
    if (!floatingShown) return;
    floatingShown = false;
    const $element = document.body.querySelector(".crear-floating");
    document.body.removeChild($element);
}

function showEditar(el) {
    if (floatingShown) return;
    floatingShown = true;
    const medico = el.parentElement.parentElement.getAttribute("data-medico");
    const parsedMedico = JSON.parse(medico);

    fetchFragment("/admin-pages/fragments/floatingEditarMedico.html").then(
        (html) => {
            const $element = document.createElement("div");
            $element.setAttribute("class", "editar-floating");
            $element.setAttribute("data-medico", medico);
            $element.innerHTML = html;

            $element.querySelector("#editar-nombre").value =
                parsedMedico.nombre;
            $element.querySelector("#editar-apellidos").value =
                parsedMedico.apellidos;
            $element.querySelector("#editar-especialidad").value =
                parsedMedico.desc_espe;
            $element.querySelector("#editar-usuario").value =
                parsedMedico.nombre_usuario;
            $element.querySelector("#editar-contrasena").value =
                parsedMedico.contrasena;

            document.body.appendChild($element);
        }
    );
}

function hideEditar() {
    if (!floatingShown) return;
    floatingShown = false;
    const $element = document.body.querySelector(".editar-floating");
    document.body.removeChild($element);
}

//------------------DATA FETCHING
async function fetchFragment(link) {
    const response = await fetch(link);
    return await response.text();
}

async function postMedico(medico) {
    const response = await fetch("/api/medicos", {
        method: "post",
        body: JSON.stringify(medico),
    });
    return await response.json();
}

async function getAllMedicos() {
    const response = await fetch("/api/medicos?type=name&query=");
    return await response.json();
}

async function getMedicosQuery(type, query) {
    const response = await fetch(`/api/medicos?type=${type}&query=${query}`);
    return await response.json();
}

async function deleteMedico(id) {
    const response = await fetch("/api/medicos", {
        method: "delete",
        body: JSON.stringify({ id }),
    });
    return await response.json();
}

async function patchMedico(medico) {
    const response = await fetch("/api/medicos", {
        method: "put",
        body: JSON.stringify(medico),
    });
    return await response.json();
}

//------------------------DOM MANIPULATION

async function addMedicoElement(medico) {
    const $list = document.body.querySelector(".medicos-list");
    const elementHtml = await fetchFragment(
        "/admin-pages/fragments/medicoElement.html"
    );

    const $element = document.createElement("div");
    $element.setAttribute("class", "medicos-element");
    $element.setAttribute("id", `medicos-element-${medico.id}`);
    $element.setAttribute("data-medico", JSON.stringify(medico));

    $element.innerHTML = elementHtml;

    $element
        .querySelector(".medicos-button-editar")
        .setAttribute("data-id", medico.id);
    $element
        .querySelector(".medicos-button-eliminar")
        .setAttribute("data-id", medico.id);
    $element
        .querySelector(".medicos-button-eliminar")
        .setAttribute("data-name", medico.nombre);
    $element
        .querySelector(".medicos-button-eliminar")
        .setAttribute("onclick", "deleteButton(this)");

    // Set the data in the element
    $element.querySelector("#display-nombre").textContent = medico.nombre;
    $element.querySelector("#display-apellidos").textContent = medico.apellidos;
    $element.querySelector("#display-especialidad").textContent =
        medico.desc_espe;
    $element.querySelector("#display-usuario").textContent =
        medico.nombre_usuario;
    $element.querySelector("#display-contrasena").textContent =
        medico.contrasena;
    $element.querySelector("#display-tipo").textContent = medico.desc_tipo;
    $element.querySelector("#display-id").textContent = medico.id;

    $list.appendChild($element);
}

function showListMessage(message) {
    const $list = document.body.querySelector(".medicos-list");
    const $warn = document.createElement("div");
    $warn.setAttribute("class", "medicos-element");
    $warn.innerHTML = `<p>${message}</p>`;
    $list.appendChild($warn);
}
