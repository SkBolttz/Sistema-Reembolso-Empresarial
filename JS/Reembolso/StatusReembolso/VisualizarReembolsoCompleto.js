document.addEventListener('DOMContentLoaded', visualizarReembolsoCompleto);

function visualizarReembolsoCompleto() {
    const token = localStorage.getItem('token');
    const nome = localStorage.getItem('nome');
    const idReembolso = localStorage.getItem('idReembolso');

    const dados = {
        nomeSolicitante: nome
    };

    fetch('http://localhost:8080/reembolso/reembolsosConcluidos', {
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
                if (reembolso.id == idReembolso) {
                    document.getElementById('id').value = reembolso.id;
                    document.getElementById('usuarioSolicitante').value = reembolso.nomeSolicitante || nome;
                    document.getElementById('valorReembolso').value = reembolso.valorReembolso;
                    document.getElementById('categoriaReembolso').value = reembolso.categoriaReembolso;
                    document.getElementById('dataValorGasto').value = reembolso.dataValorGasto;
                    document.getElementById('comprovante').value = reembolso.comprovante;
                    document.getElementById('justificativaResponsavel').value = reembolso.justificativaResponsavel || '';
                    document.getElementById('status').value = reembolso.status;
                    document.getElementById('dataAbertura').value = reembolso.dataAbertura;
                    document.getElementById('dataAprovacao').value = reembolso.dataAprovacao ? reembolso.dataAprovacao : 'Sem data de aprovação';
                    document.getElementById('responsavel').value = reembolso.nomeResponsavel ? reembolso.nomeResponsavel : 'Sem responsável';
                }
            });
        })
        .catch(error => {
            console.error('Erro ao carregar reembolso:', error);
        });
}
