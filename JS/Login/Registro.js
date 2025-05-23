document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('cadastro-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const nome = document.getElementById('username');
        const email = document.getElementById('email');
        const cpf = document.getElementById('cpf');
        const senha = document.getElementById('password');
        const tipo = document.getElementById('role');

        const dados = {
            nome: nome.value,
            email: email.value,
            cpf: cpf.value,
            senha: senha.value,
            tipo: tipo.value
        }

        if (termos.checked == false) {
            var termoErro = document.getElementById('termos-erro');
            termoErro.style.display = 'block';
            return
        }

        if (nome.value == '' || email.value == '' || cpf.value == '' || senha.value == '' || tipo.value == '') {
            var erroCampo = document.getElementById('erro-campo-nulo');
            erroCampo.style.display = 'block';
            return
        }

        // Enviar os dados para o servidor
        fetch('http://localhost:8080/api/auth/registrar/usuario', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        })
            .then(response => response.json())
            .then(token => {

                window.location.href = '../../HTML/Login/TelaLogin.html';
            })
            .catch(async (error) => {
                const erroNome = document.getElementById('nome-erro');
                const erroEmail = document.getElementById('email-erro');
                const erroCPF = document.getElementById('cpf-erro');

                const erroJson = await error.json?.();
                const msg = erroJson?.msg || error.message;

                if (msg === 'Nome de usuário ja cadastrado!') {
                    erroNome.style.display = 'block';
                }
                if (msg === 'Email de usuário ja cadastrado!') {
                    erroEmail.style.display = 'block';
                }
                if (msg === 'Cpf ja╠ü cadastrado!') {
                    erroCPF.style.display = 'block';
                }
            });
    });
});