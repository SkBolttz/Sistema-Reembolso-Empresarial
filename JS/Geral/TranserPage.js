document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('IconPerson').addEventListener('click', function (event) {
        event.preventDefault();

        const token = localStorage.getItem('token');

        if (token) {
            const payloadBase64 = token.split('.')[1];
            const padded = payloadBase64.padEnd(payloadBase64.length + (4 - payloadBase64.length % 4) % 4, '=');
            const payloadDecoded = JSON.parse(atob(padded));
            const tipoUsuario = payloadDecoded.tipo;

            console.log('Tipo do usuário (role):', tipoUsuario);

            if (tipoUsuario == 'FUNCIONARIO') {
                window.location.href = '../../../HTML/Pagina-Inicial/PaginaInicial.html';
            } else {
                window.location.href = '../../../HTML/Pagina-Inicial/PaginaInicialAdmin.html';
            }
        }
    });
});
