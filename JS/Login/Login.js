document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('login-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const senha = document.getElementById('senha').value;

        const data = {
            nome: nome,
            senha: senha
        };

        const loginNulo = document.getElementById('login-nulo');
        const erroLogin = document.getElementById('erro-login');

        loginNulo.style.display = 'none';
        erroLogin.style.display = 'none';

        if (senha == '' || nome == '') {
            loginNulo.style.display = 'block';
            return
        }

        fetch('http://localhost:8080/api/auth/login/usuario', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(async response => {
                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.erro || 'Erro desconhecido');
                }
                return response.text();
            })
            .then(token => {
                localStorage.setItem('token', token);
                localStorage.setItem('nome', nome);

                if (token) {
                    const payloadBase64 = token.split('.')[1];
                    const padded = payloadBase64.padEnd(payloadBase64.length + (4 - payloadBase64.length % 4) % 4, '=');
                    const payloadDecoded = JSON.parse(atob(padded));
                    const tipoUsuario = payloadDecoded.tipo;

                    if (tipoUsuario === 'FUNCIONARIO') {
                        window.location.href = '../../HTML/Pagina-Inicial/PaginaInicial.html';
                    } else {
                        window.location.href = '../../HTML/Pagina-Inicial/PaginaInicialAdmin.html';
                    }

                }
            })
            .catch(error => {
                erroLogin.style.display = 'block';
            });
    });
});