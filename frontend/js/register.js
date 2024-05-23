document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        if (!form.dataset.submitting) {
            form.dataset.submitting = 'true';
            buttonRegister();
        }
    });
});

function buttonRegister() {
    var user = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var confirmPassword = document.getElementById('confirmPassword').value;

    if (password != confirmPassword) {
        alert("Senhas diferentes. Tente novamente!")
        throw new Error("Senha diferente.")
    }


    var dados = {
        register: user,
        password: password
    }

    fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
        .then(() => {
            alert("Registro realizado com sucesso!");
            window.location.href = "../login.html";

        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Registro inválido!');
        })
        .finally(() => {
            const form = document.querySelector('form');
            delete form.dataset.submitting;
        });
}
