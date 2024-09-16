//------------------ON LOAD

function loadInfo() {
    console.log("loading info");

    document.querySelector(".consultas-list").innerHTML = "";

    const $list = document.querySelector(".consultas-list");
    fetchAllconsultas().then((json) => {
        console.log(json);

        if (json.length == 0) {
            showNoconsultasAvailable();
        } else {
            json.forEach((cita) => addCitaElement(cita));
        }
    });
}

//------------------SHOW FLOATING MENUS
var floatingShown = false;

function showCrear() {
    if (floatingShown) return;
    floatingShown = true;
    fetchFragment("/medico/fragments/floatingCrearConsulta.html").then(
        (html) => {
            const $element = document.createElement("div");
            $element.setAttribute("class", "crear-floating");
            $element.innerHTML = html;
            $element.querySelector("#crear-hora").setAttribute("disabled", "");

            // Set the available medicos
            const $options = $element.querySelector("#paciente-options");
            const $servicesOptions =
                $element.querySelector("#servicio-options");

            fetchAvailablePacientes()
                .then((json) => {
                    let optionElements = "";
                    for (key in json) {
                        console.log(json);

                        optionElements += `<option data-name="${json[key].nombre}" data-id="${json[key].id_paciente}" value="${json[key].nombre}">${json[key].nombre}</option>`;
                    }
                    $options.innerHTML = optionElements;
                    return fetchAvailableServices(); // Fetch available services
                })
                .then((json) => {
                    // Set the available services
                    let servicesOptions = "";
                    for (key in json) {
                        servicesOptions += `<option data-desc="${json[key].desc_servicio}" data-id="${json[key].id_servicio}" value="${json[key].desc_servicio}">${json[key].desc_servicio}</option>`;
                    }
                    $servicesOptions.innerHTML = servicesOptions;
                })
                .finally(() => {
                    document.body.appendChild($element);
                });
        }
    );
}

function hideCrear() {
    if (!floatingShown) return;
    floatingShown = false;
    const $element = document.body.querySelector(".crear-floating");
    document.body.removeChild($element);
}

//------------------MENU FUNCTIONALITIES
function allowTimes() {
    console.log("let's see available times");

    const date = new Date(document.querySelector("#crear-fecha").value);
    console.log(date);
    const sendDate = {
        day: date.getDate() + 1,
        month: date.getMonth() + 1,
        year: date.getFullYear(),
    };
    console.log(sendDate);

    fetchAvailableTimes(sendDate).then((json) => {
        console.log(json);
        const $options = document.querySelector("#time-options");
        let options = "";
        json.forEach(
            (time) => (options += `<option value="${time}">${time}</option>`)
        );
        $options.innerHTML = options;
        document.querySelector("#crear-hora").removeAttribute("disabled");
    });
}

function createButton() {
    const $floating = document.querySelector(".crear-floating");
    const date = $floating.querySelector("#crear-fecha").value;
    const time = $floating.querySelector("#crear-hora").value;
    const nombre_paciente = $floating.querySelector("#crear-paciente").value;
    const id_paciente = $floating
        .querySelector(`[data-name="${nombre_paciente}"]`)
        .getAttribute("data-id");

    const [hour, minute] = time.split(":");

    const desc_servicio = $floating.querySelector("#crear-servicio").value;
    const id_servicio = $floating
        .querySelector(`[data-desc="${desc_servicio}"]`)
        .getAttribute("data-id");

    const fecha = new Date(date);
    fecha.setHours(hour);
    fecha.setMinutes(minute);

    fecha.setDate(fecha.getDate() + 1);

    if (
        fecha.toLocaleString("default", {
            weekday: "short",
        }) == "dom"
    ) {
        alert("No hay consultas los domingos!");
        return;
    }

    const cita = {
        year: fecha.getFullYear(),
        month: fecha.getMonth() + 1,
        day: fecha.getDate(),
        hour: fecha.getHours(),
        minutes: fecha.getMinutes(),
        id_paciente,
        id_servicio,
    };

    console.log(cita);

    postCita(cita)
        .then((json) => {
            console.log(json);
            if (json.status) {
                alert("La consulta se agendo correctamente");
            }
            if (json.error) {
                alert("Hubo un error");
            }
        })
        .finally(() => {
            loadInfo();
            hideCrear();
        });
}
function eliminarButton(el) {
    const res = confirm("Estas seguro de eliminar la cita?");
    if (!res) return;

    const id_cita = el.getAttribute("data-id");
    deleteConsulta(id_cita)
        .then((json) => {
            if (json.status) {
                alert("La consulta se elimino con exito");
            }
            if (json.error) {
                alert("Hubo un error");
            }
        })
        .finally(() => {
            loadInfo();
        });
}

//------------------DATA FETCHING
async function fetchFragment(link) {
    const response = await fetch(link);
    return await response.text();
}

async function fetchAvailableTimes({ year, month, day }) {
    const res = await fetch(
        `/api/consultas?type=available-times&year=${year}&month=${month}&day=${day}`
    );
    return await res.json();
}

async function fetchAvailablePacientes() {
    const res = await fetch("/api/consultas?type=available-pacientes");
    return await res.json();
}

async function fetchAvailableServices() {
    const res = await fetch("/api/consultas?type=available-services");
    return await res.json();
}

async function postCita(cita) {
    const res = await fetch("/api/consultas", {
        method: "post",
        body: JSON.stringify(cita),
    });
    return await res.json();
}

async function fetchAllconsultas() {
    const res = await fetch(`/api/consultas?type=all-consultas`);
    return await res.json();
}

async function deleteConsulta(id) {
    const res = await fetch(`/api/consultas?id=${id}`, {
        method: "delete",
    });
    return await res.json();
}

//-------------------LIST UPDATES

function showNoconsultasAvailable() {
    const $list = document.querySelector(".consultas-list");
    const $warn = document.createElement("div");
    $warn.setAttribute("class", "cita-element");
    $warn.innerText = "No hay consultas que mostrar";
    $list.appendChild($warn);
}

function addCitaElement(cita) {
    const $list = document.querySelector(".consultas-list");
    const $element = document.createElement("div");
    $element.setAttribute("class", "cita-element");

    console.log("cita");
    console.log(cita);

    fetchFragment("/paciente/fragments/citaElement.html").then((html) => {
        $element.innerHTML = html;

        $element.querySelector(
            "#display-hora"
        ).innerText = `${cita.fecha_consulta[3]}:${cita.fecha_consulta[4]}`;
        $element.querySelector(
            "#display-fecha"
        ).innerText = `${cita.fecha_consulta[0]}/${cita.fecha_consulta[1]}/${cita.fecha_consulta[2]}`;

        $element.querySelector(
            "#display-medico"
        ).innerText = `${cita.medico.nombre_medico} ${cita.medico.apellidos_medico}`;
        $element.querySelector(
            "#display-paciente"
        ).innerText = `${cita.paciente.nombre} ${cita.paciente.apellidos}`;
        $element.querySelector("#display-servicio").innerText =
            cita.servicio.desc_servicio;
        $element
            .querySelector(".cita-button")
            .setAttribute("data-id", cita.id_consulta);

        // Add the thing
        $list.appendChild($element);
    });
}
