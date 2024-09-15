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
        console.log($options);

        fetchAvailableDoctors()
            .then((json) => {
                console.log(json);
                let optionElements = "";
                for (key in json) {
                    optionElements += `<option id="medico-option-${json[key].nombre_medico}" data-id="${json[key].id_medico}" value="${json[key].nombre_medico}">${json[key].nombre_medico}</option>`;
                }
                $options.innerHTML = optionElements;
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
