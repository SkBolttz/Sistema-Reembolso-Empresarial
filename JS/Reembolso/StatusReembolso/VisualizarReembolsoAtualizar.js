document.addEventListener('DOMContentLoaded', visualizarReembolsoAtualizar);

function visualizarReembolsoAtualizar() {

    const token = localStorage.getItem('token');

    const dados = {
        idReembolso: localStorage.getItem('idCompleto')
    };

    fetch('http://localhost:8080/reembolso/ReembolsoAtualizar', {
        method: 'POST',
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
        .then(reembolsos => {

            reembolsos.forEach(reembolso => {
                if (reembolso.id == localStorage.getItem('idCompleto')) {
                    document.getElementById('usuarioSolicitante').value = localStorage.getItem('nomeSolicitante');
                    document.getElementById('valorReembolso').value = reembolso.valorReembolso;
                    document.getElementById('categoriaReembolso').value = reembolso.categoriaReembolso;
                    document.getElementById('dataValorGasto').value = reembolso.dataValorGasto;
                    document.getElementById('comprovante').value = reembolso.comprovante;
                    document.getElementById('id').value = localStorage.getItem('idCompleto')    ;
                    document.getElementById('usuarioResponsavel').value = localStorage.getItem('nome');
                }
            });
        })
        .catch(error => {
            console.error(error);
        });
}