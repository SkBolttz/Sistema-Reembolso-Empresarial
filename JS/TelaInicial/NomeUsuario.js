document.addEventListener('DOMContentLoaded', function () {

    const nome = localStorage.getItem('nome');

    if (nome) {
        document.getElementById('nome-usuario').textContent = `Bem-vindo, ${nome}!`;
    }
});