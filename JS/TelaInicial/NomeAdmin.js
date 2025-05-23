document.addEventListener('DOMContentLoaded', function () {

    const nome = localStorage.getItem('nome');

    if (nome) {
        document.getElementById('nome-admin').textContent = `Bem-vindo ao Painel Administrativo, ${nome}!`;
    }
});