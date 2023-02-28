// Call the dataTables jQuery plugin
$(document).ready(function () {
    //nada por ahora
});

async function iniciarSesion() {
    let userData = {};
    userData.email = document.getElementById("txtEmail").value;
    userData.password = document.getElementById("txtPassword").value;


    const request = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });

    const respuesta = await request.text();
    if (respuesta !='FAIL') {
        //guardamos la sesion en el localstorage(browser del cliente)
        localStorage.token = respuesta;//respuesta con el token y credenciales
        localStorage.email = userData.email;//almacenamos el email del usuario también en el localstorage
        window.location.href = "users.html";
    }else{
        alert("Las credenciales son incorrectas. Por favor intente nuevamente.");
    }
}