//------------------SHOW FLOATING MENUS
var floatingShown = false;

function showCrear() {
    if (floatingShown) return;
    floatingShown = true;
    fetchFragment("/paciente/fragments/floatingCrearCita.html").then((html) => {
        const $element = document.createElement("div");
        $element.setAttribute("class", "crear-floating");
        $element.innerHTML = html;
        document.body.appendChild($element);
    });
}

//------------------DATA FETCHING
async function fetchFragment(link) {
    const response = await fetch(link);
    return await response.text();
}
