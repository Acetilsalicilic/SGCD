function loadInfo() {
    fetchConsultas().then((json) => {
        console.log(json);

        if (json.length == 0) {
            showNoconsultasAvailable();
            return;
        }

        for (key in json) {
            addCitaElement(json[key]);
        }
    });
}

async function fetchConsultas() {
    const res = await fetch(`/api/consultas?type=all-consultas&for=paciente`);
    return await res.json();
}

async function fetchFragment(url) {
    const res = await fetch(url);
    return await res.text();
}

function addCitaElement(cita) {
    const $list = document.querySelector(".citas-list");
    const $element = document.createElement("div");
    $element.setAttribute("class", "cita-element");

    console.log("cita");
    console.log(cita);

    fetchFragment("/paciente/fragments/consultaElement.html").then((html) => {
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

        // Add the thing
        $list.appendChild($element);
    });
}

function showNoconsultasAvailable() {
    const $list = document.querySelector(".citas-list");
    const $warn = document.createElement("div");
    $warn.setAttribute("class", "cita-element");
    $warn.innerText = "No hay consultas que mostrar";
    $list.appendChild($warn);
}
