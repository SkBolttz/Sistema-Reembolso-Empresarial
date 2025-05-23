document.addEventListener('DOMContentLoaded', function () {

    const token = localStorage.getItem('token');
    const idCompleto = localStorage.getItem('idCompleto');

    const dados = {
        idReembolso: idCompleto
    };

    fetch('http://localhost:8080/reembolso/verificarSolicitante', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(dados)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar o nome do solicitante");
            }
            return response.text();
        })
        .then(nomeSolicitante => {
            localStorage.setItem('nomeSolicitante', nomeSolicitante);
        })
        .catch(error => {
            console.error('Erro ao buscar nome do solicitante:', error);
        });
});
