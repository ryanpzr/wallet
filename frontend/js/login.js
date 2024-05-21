document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        fetchToken();
    });
});

function fetchToken() {
    try {
        var user = document.getElementById('username').value;
        var password = document.getElementById('password').value;

        var dados = {
            login: user,
            password: password
        }

        fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao enviar os dados:' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.token) {
                    var token = data.token;
                    sessionStorage.setItem('token', token);
                    alert("Login realizado com sucesso!");
                    window.location.href = "mainPage.html";
                } else {
                    alert("Login inválido!");
                    throw new Error('Login inválido!');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
            });

    } catch (error) {
        console.error('Erro ao enviar os dados para o backend:', error);
    }
}
