document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        if (!form.dataset.submitting) {
            form.dataset.submitting = 'true';
            fetchToken();
        }
    });
});

function fetchToken() {
    var user = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var dados = {
        login: user,
        password: password
    }

    fetch('https://backend-production-4f9d.up.railway.app/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Login inválido!');
            }
            return response.json();
        })
        .then(data => {
            if (data.token) {
                var token = data.token;
                var timestamp = new Date().getTime();
                sessionStorage.setItem('token', token);
                sessionStorage.setItem('tokenTimestamp', timestamp);
                alert("Login realizado com sucesso!");
                window.location.href = "html/mainPage.html";
            } else {
                throw new Error('Login inválido!');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Login inválido!');
        })
        .finally(() => {
            const form = document.querySelector('form');
            delete form.dataset.submitting;
        });
}
