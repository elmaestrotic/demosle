// Call the dataTables jQuery plugin
$(document).ready(function () {
    cargarUsuarios();
    $('#usuarios').DataTable();
    actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario() {//tomamos el campo del email del usuario y lo reemplazamos por el email del usuario que está logueado
    document.getElementById('txtmailuser').outerHTML = localStorage.email;
}//txtmailuser es el id del campo del email del usuario en usuarios.html



async function cargarUsuarios() {
    const request = await fetch('api/users', {
        method: 'GET',
        headers: getHeaders()
    });
    const usuarios = await request.json();


    let listadoHtml = '';
    for (let usuario of usuarios) {
        let botonEliminar = '<a href="#" onclick="deleteUser(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

        let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;
        let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>'
            + usuario.email+'</td><td>'+telefonoTexto
            + '</td><td>' + botonEliminar + '</td></tr>';
        listadoHtml += usuarioHtml;
    }

    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;

}



function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token//enviamos el token en la cabecera de la petición
    };
}

async function deleteUser(id) {
    if (!confirm("¿Está seguro de eliminar el usuario?")) {
        return;
    }

    const request = await fetch('api/del_user/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload();//actualizamos la página luego de eliminar el usuario
}
