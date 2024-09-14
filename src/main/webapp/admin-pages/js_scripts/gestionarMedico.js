//--------------------ON PAGE LOAD

function loadInfo() {
    getAllMedicos().then((info) => {
        console.log("loading info");
        const data = convertMedicos(info);
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
            data.forEach((medico) => addMedicoElement(medico));
        }
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
    return await response.json;
}

//------------------------DOM MANIPULATION

async function addMedicoElement(medico) {
    console.log("adding element");

    console.log(medico);

    const $list = document.body.querySelector(".medicos-list");
    const elementHtml = await fetchFragment(
        "/admin-pages/fragments/medicoElement.html"
    );

    const $element = document.createElement("div");
    $element.setAttribute("class", "medicos-element");
    $element.setAttribute("id", `medicos-element-${medico.id}`);

    $element.innerHTML = elementHtml;

    $element
        .querySelector(".medicos-button-editar")
        .setAttribute("data-id", medico.id);
    $element
        .querySelector(".medicos-button-eliminar")
        .setAttribute("data-id", medico.id);

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
