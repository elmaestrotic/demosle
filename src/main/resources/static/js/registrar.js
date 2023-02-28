// Call the dataTables jQuery plugin
$(document).ready(function () {
    //nada por ahora
});

async function registrarUser() {
    let userData = {};
    userData.nombre = document.getElementById("txtName").value;
    userData.apellido = document.getElementById("txtLastName").value;
    userData.email = document.getElementById("txtEmail").value;
    userData.password = document.getElementById("txtPassword").value;

    let repetirPassword = document.getElementById("txtRepeatPassword").value;

    if (userData.password != repetirPassword) {
        alert("Las contraseñas no coinciden");
        return;//se sale de la función
    }
    const request = await fetch('api/users', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });
    alert("¡¡Registrado exitosamente!!");
    window.location.href = "login.html";
}

function limpiarFormulario() {
    document.getElementById("userform").reset();
}

function limpiar() {
    alert("Registro exitoso");
    document.addEventListener('DOMContentLoaded', function () {
        let formulario = document.getElementById('userform');
        formulario.addEventListener('submit', function () {
            formulario.reset();
        });
    });
}


/*Este código utiliza el método HTTP POST para enviar los datos del usuario al servidor en el cuerpo de la solicitud.
    Los datos del usuario se pasan en el parámetro "userData", que debe ser un objeto JSON que contiene la información del usuario a agregar.
    El servidor puede recibir y procesar estos datos para agregar un nuevo usuario a la base de datos o almacenarlos de alguna otra manera.
    Una vez que se agrega el usuario, la página se actualiza para reflejar los cambios.*/
