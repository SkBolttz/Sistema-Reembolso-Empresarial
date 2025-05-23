document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("form-reembolso-2").addEventListener("submit", function (event) {
        event.preventDefault();

        const token = localStorage.getItem('token');
        const idCompleto = localStorage.getItem('idCompleto');

        const responsavel = localStorage.getItem('nome');
        const justificativaResponsavel = document.getElementById('justificativaResponsavel').value;
        const status = document.getElementById('status').value;

        const dados = {
            id: idCompleto,
            responsavel: responsavel,
            justificativaResponsavel: justificativaResponsavel,
            status: status
        };

        fetch('http://localhost:8080/reembolso/alterarStatusReembolso', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(dados)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro na resposta da API");
                }
                return response.json();
            })
            .then(reembolso => {
                var sucesso = document.getElementById('sucesso-atualizacao');
                sucesso.style.display = 'block';

                var button = document.getElementById('buttonUpdate');
                button.disabled = true;
                button.style.opacity = '0.1';
                button.style.cursor = 'not-allowed';
            })
            .catch(error => {
                var erro = document.getElementById('erro-atualizacao');
                erro.style.display = 'block';
            });
    });
});