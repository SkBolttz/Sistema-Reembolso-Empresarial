document.addEventListener('DOMContentLoaded', function () {
    const nomeSalvo = localStorage.getItem('nome');
    if (nomeSalvo) {
        document.getElementById('nomeSolicitante').value = nomeSalvo;
    }

    document.getElementById('form-reembolso').addEventListener('submit', function (event) {
        event.preventDefault();

        const token = localStorage.getItem('token');

        const nomeSolicitante = localStorage.getItem('nome');
        const valorReembolso = document.getElementById('valor').value;
        const categoriaReembolso = document.getElementById('categoria').value;
        const dataValorGasto = document.getElementById('data').value;
        const justificativa = document.getElementById('descricao').value;
        const comprovante = document.getElementById('comprovante').files[0];

        const erro_nulo = document.getElementById('erro-solicitacao');
        const sucesso = document.getElementById('sucesso-solicitacao');
        const erro_valor = document.getElementById('erro-valor');
        const erro_data = document.getElementById('erro-data');

        erro_nulo.style.display = 'none';
        sucesso.style.display = 'none';
        erro_valor.style.display = 'none';
        erro_data.style.display = 'none';

        if (!nomeSolicitante || !valorReembolso || !categoriaReembolso || !dataValorGasto || !justificativa || !comprovante) {
            erro_nulo.style.display = 'block';
            return;
        }

        if(valorReembolso <= 0){
            erro_valor.style.display = 'block';
            return;
        }

        if(dataValorGasto > new Date().toISOString().split('T')[0]){
            erro_data.style.display = 'block';
            return;
        }

        const dto = {
            nomeSolicitante,
            valorReembolso: parseFloat(valorReembolso),
            categoriaReembolso,
            dataValorGasto,
            justificativa
        };

        const formData = new FormData();

        formData.append("dto", new Blob(
            [JSON.stringify(dto)],
            { type: "application/json" }
        ));
        formData.append("comprovante", comprovante);

        fetch('http://localhost:8080/reembolso/solicitar', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                sucesso.style.display = 'block';
                document.getElementById('form-reembolso').reset();
                document.getElementById('nomeSolicitante').value = nomeSalvo;
            })
            .catch(error => {
                const erro = error.response?.data?.msg || error.message;

                if(erro.includes('Data do reembolso nao pode ser futura!')){
                    document.getElementById('erro-data').style.display = 'block';
                }
            });
    });
});
