document.addEventListener('DOMContentLoaded', listaReembolsoAprovacao);

function listaReembolsoAprovacao() {
    const token = localStorage.getItem('token');

    const dados = {
        responsavel: localStorage.getItem('nome')
    }

    fetch('http://localhost:8080/reembolso/todosAbertos', {
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
            const container = document.getElementById('reembolsos-container');
            container.innerHTML = '';

            reembolsos.sort((a, b) => b.id - a.id);

            reembolsos.forEach(reembolso => {
                const card = document.createElement('div');
                card.classList.add('card', 'aberto');
                card.style.cursor = 'pointer';

                card.onclick = () => {
                    localStorage.setItem('idCompleto', reembolso.id);
                    window.location.href = '../../../HTML/Reembolso/AtualizarReembolso/AtualizarReembolsoForm.html';
                };

                if (reembolso.status != 'Aprovado') {
                    card.classList.remove('aberto');
                    card.classList.add('pendente');
                }

                card.innerHTML = `
                <p><strong>ID do Reembolso:</strong> ${reembolso.id}</p>
                <p><strong>Data do Pedido:</strong> ${formatarData(reembolso.dataValorGasto)}</p>
                <p><strong>Categoria:</strong> ${reembolso.categoriaReembolso}</p>
                <p><strong>Descrição:</strong> ${reembolso.justificativaSolicitante}</p>
                <p><strong>Valor Total:</strong> R$ ${Number(reembolso.valorReembolso).toFixed(2)}</p>
                <p><strong>Status:</strong> ${reembolso.status}</p>
            `;

                container.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Erro ao buscar reembolsos pendentes:', error);
        });
}

function formatarData(dataISO) {
    const data = new Date(dataISO);
    return data.toLocaleDateString('pt-BR');
}
