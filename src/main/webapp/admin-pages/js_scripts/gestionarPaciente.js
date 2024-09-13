var editarShown = false;

const showEditar = (el) => {
    if (editarShown) return;

    editarShown = true;

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
    if (!editarShown) return;
    editarShown = false;

    const $floating = document.getElementById("editar-floating");
    document.body.removeChild($floating);
};
