var floatingShown = false;

const showEditar = (el) => {
    if (floatingShown) return;

    floatingShown = true;

    let editarString;

    fetch("/admin-pages/fragments/floatingEditPaciente.html")
        .then((response) => response.text())
        .then((text) => {
            console.log(text);
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
