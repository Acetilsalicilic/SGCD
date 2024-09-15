//------------------ON LOAD

function loadInfo() {
    console.log("loading info");

    document.querySelector(".citas-list").innerHTML = "";

    const $list = document.querySelector(".citas-list");
    fetchAllCitas().then((json) => {
        console.log(json);

        if (json.length == 0) {
            showNoCitasAvailable();
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
    fetchFragment("/paciente/fragments/floatingCrearCita.html").then((html) => {
        const $element = document.createElement("div");
        $element.setAttribute("class", "crear-floating");
        $element.innerHTML = html;
        $element.querySelector("#crear-hora").setAttribute("disabled", "");

        // Set the available medicos
        const $options = $element.querySelector("#medico-options");
        const $servicesOptions = $element.querySelector("#servicio-options");
        console.log($options);

        fetchAvailableDoctors()
            .then((json) => {
                console.log(json);
                let optionElements = "";
                for (key in json) {
                    optionElements += `<option id="medico-option-${json[key].nombre_medico}" data-id="${json[key].id_medico}" value="${json[key].nombre_medico}">${json[key].nombre_medico}</option>`;
                }
                $options.innerHTML = optionElements;
                return fetchAvailableServices();
            })
            .then((json) => {
                // Set the available services
                console.log(json);
                let servicesOptions = "";
                for (key in json) {
                    servicesOptions += `<option data-desc="${json[key].desc_servicio}" data-id="${json[key].id_servicio}" value="${json[key].desc_servicio}">${json[key].desc_servicio}</option>`;
                }
                $servicesOptions.innerHTML = servicesOptions;
            })
            .finally(() => {
                document.body.appendChild($element);
            });
    });
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

    const medico = document.querySelector("#crear-medico").value;
    const id_medico = document
        .querySelector(`#medico-option-${medico}`)
        .getAttribute("data-id");
    const date = new Date(document.querySelector("#crear-fecha").value);
    console.log(date);
    const sendDate = {
        day: date.getDate() + 1,
        month: date.getMonth() + 1,
        year: date.getFullYear(),
    };
    console.log(sendDate);

    fetchAvailableTimes(id_medico, sendDate).then((json) => {
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
    const nombre_medico = $floating.querySelector("#crear-medico").value;
    const id_medico = $floating
        .querySelector(`#medico-option-${nombre_medico}`)
        .getAttribute("data-id");

    const [hour, minute] = time.split(":");

    const desc_servicio = $floating.querySelector("#crear-servicio").value;
    const id_servicio = $floating
        .querySelector(`[data-desc="${desc_servicio}"]`)
        .getAttribute("data-id");

    const fecha = new Date(date);
    fecha.setHours(hour);
    fecha.setMinutes(minute);
    console.log(fecha);

    const cita = {
        year: fecha.getFullYear(),
        month: fecha.getMonth() + 1,
        day: fecha.getDate() + 1,
        hour: fecha.getHours(),
        minutes: fecha.getMinutes(),
        id_medico,
        id_servicio,
    };

    console.log(cita);

    postCita(cita).then((json) => {
        console.log(json);
    });
}

//------------------DATA FETCHING
async function fetchFragment(link) {
    const response = await fetch(link);
    return await response.text();
}

async function fetchAvailableTimes(id_medico, { year, month, day }) {
    const res = await fetch(
        `/api/citas?type=available-times&id_medico=${id_medico}&year=${year}&month=${month}&day=${day}`
    );
    return await res.json();
}

async function fetchAvailableDoctors() {
    const res = await fetch("/api/citas?type=available-medicos");
    return await res.json();
}

async function fetchAvailableServices() {
    const res = await fetch("/api/citas?type=available-services");
    return await res.json();
}

async function postCita(cita) {
    const res = await fetch("/api/citas", {
        method: "post",
        body: JSON.stringify(cita),
    });
    return await res.json();
}

async function fetchAllCitas() {
    const res = await fetch(`/api/citas?type=all-citas`);
    return await res.json();
}

//-------------------LIST UPDATES

function showNoCitasAvailable() {
    const $list = document.querySelector(".citas-list");
    const $warn = document.createElement("div");
    $warn.setAttribute("class", "cita-element");
    $warn.innerText = "No hay citas que mostrar";
    $list.appendChild($warn);
}

function addCitaElement(cita) {
    const $list = document.querySelector(".citas-list");
    const $element = document.createElement("div");
    $element.setAttribute("class", "cita-element");

    fetchFragment("/paciente/fragments/citaElement.html").then((html) => {
        $element.innerHTML = html;

        $element.querySelector(
            "#display-hora"
        ).innerText = `${cita.fecha_cita[3]}:${cita.fecha_cita[4]}`;
        $element.querySelector(
            "#display-fecha"
        ).innerText = `${cita.fecha_cita[0]}/${cita.fecha_cita[1]}/${cita.fecha_cita[2]}`;

        $element.querySelector(
            "#display-medico"
        ).innerText = `${cita.medico.nombre_medico} ${cita.medico.apellidos_medico}`;
        $element.querySelector(
            "#display-paciente"
        ).innerText = `${cita.paciente.nombre} ${cita.paciente.nombre.apellidos}`;
        $element.querySelector("#display-servicio").innerText =
            cita.servicio.desc_servicio;
        $list.appendChild($element);
    });
}
