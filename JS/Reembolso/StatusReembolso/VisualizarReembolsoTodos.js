document.addEventListener('DOMContentLoaded', visualizarReembolsoCompleto);

function visualizarReembolsoCompleto() {
    const token = localStorage.getItem('token');
    const nome = localStorage.getItem('nome');
    const idReembolso = localStorage.getItem('idReembolso');

    fetch('http://localhost:8080/reembolso/todos', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
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

                    if (reembolso.status == 'PENDENTE') {
                        console.log(reembolsos);

                        document.getElementById('id').value = reembolso.id;
                        document.getElementById('usuarioSolicitante').value = reembolso.nomeSolicitante || nome;
                        document.getElementById('responsavel').value = reembolso.nomeResponsavel || "Sem responsável";
                        document.getElementById('justificativaSolicitante').value = reembolso.justificativaSolicitante;
                        document.getElementById('dataValorGasto').value = reembolso.dataValorGasto;
                        document.getElementById('dataAprovacao').value = reembolso.dataAprovacao || "Sem data de aprovação";
                        document.getElementById('dataAbertura').value = reembolso.dataAbertura;
                        document.getElementById('valorReembolso').value = reembolso.valorReembolso;
                        document.getElementById('categoriaReembolso').value = reembolso.categoriaReembolso;
                        document.getElementById('comprovante').value = reembolso.comprovante;
                        document.getElementById('status').value = reembolso.status;
                    } else {
                        console.log(reembolsos);

                        document.getElementById('id').value = reembolso.id;
                        document.getElementById('usuarioSolicitante').value = reembolso.nomeSolicitante || nome;
                        document.getElementById('responsavel').value = reembolso.nomeResponsavel;
                        document.getElementById('valorReembolso').value = reembolso.valorReembolso;
                        document.getElementById('justificativaResponsavel').value = reembolso.justificativaResponsavel;
                        document.getElementById('dataValorGasto').value = reembolso.dataValorGasto;
                        document.getElementById('dataAprovacao').value = reembolso.dataAprovacao;
                        document.getElementById('dataAbertura').value = reembolso.dataAbertura;
                        document.getElementById('categoriaReembolso').value = reembolso.categoriaReembolso;
                        document.getElementById('comprovante').value = reembolso.comprovante;
                        document.getElementById('status').value = reembolso.status;
                    }

                }
            });
        })
        .catch(error => {
            console.error('Erro ao carregar reembolso:', error);
        });
}
