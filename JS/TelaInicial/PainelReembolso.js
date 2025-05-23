document.addEventListener('DOMContentLoaded', function () {

    function reembolsoPendente() {
        const token = localStorage.getItem('token');

        const StatusPagamento = 'PENDENTE';

        const dados = {
            nomeSolicitante: localStorage.getItem('nome'),
            status: StatusPagamento
        }

        fetch('http://localhost:8080/reembolso/consultarPorStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(dados)
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('count-pendente').innerHTML = data.length;
            })
            .catch(error => {
                console.error('Erro ao buscar reembolsos pendentes:', error);
            });
    }

        function reembolsoCancelado() {
        const token = localStorage.getItem('token');

        const StatusPagamento = 'RECUSADO';

        const dados = {
            nomeSolicitante: localStorage.getItem('nome'),
            status: StatusPagamento
        }

        fetch('http://localhost:8080/reembolso/consultarPorStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(dados)
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('count-recusado').innerHTML = data.length;
            })
            .catch(error => {
                console.error('Erro ao buscar reembolsos pendentes:', error);
            });
    }

            function reembolsoPago() {
        const token = localStorage.getItem('token');

        const StatusPagamento = 'PAGO';

        const dados = {
            nomeSolicitante: localStorage.getItem('nome'),
            status: StatusPagamento
        }

        fetch('http://localhost:8080/reembolso/consultarPorStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(dados)
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('count-aprovado').innerHTML = data.length;
            })
            .catch(error => {
                console.error('Erro ao buscar reembolsos pendentes:', error);
            });
    }

    reembolsoPago();
    reembolsoCancelado();
    reembolsoPendente();
});