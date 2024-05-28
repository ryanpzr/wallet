document.addEventListener('DOMContentLoaded', function () {
    event.preventDefault();

    var token = sessionStorage.getItem('token'); // Recuperando o token do sessionStorage

    if (token == null) {
        throw new Error("Faça login para aparecer as informações!")

    } else {
        const main = document.getElementById('listAll');

        function carregarDadosReceita() {
            event.preventDefault();

            fetch('https://backend-production-4f9d.up.railway.app/api/income?sort=id', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token // Adicionando o token JWT ao cabeçalho de autorização
                }
            })
                .then(response => {
                    if (response.ok) {
                        return response.json(); // Convertendo a resposta para JSON
                    } else {
                        throw new Error('Erro ao buscar dados do servidor.');
                    }
                })
                .then(data => {
                    if (data.content && Array.isArray(data.content)) {
                        // Verifica se a propriedade "content" existe e se é um array
                        data.content.forEach(card => {
                            const novoItem = criarElementoCard(card);
                            main.appendChild(novoItem);
                        });
                    } else {
                        throw new Error('Formato de dados inválido: propriedade "content" não encontrada ou não é um array.');
                    }
                })
                .catch(error => {
                    console.error('Erro ao buscar dados:', error.message);
                });
        }

        carregarDadosReceita();

        function criarElementoCard(card) {
            const novoItem = document.createElement('div');
            novoItem.classList.add('cardStyle');

            novoItem.innerHTML = `
        <h1 class="cardH1">${card.mes}</h1><br>
        <p class="cardP">Empresa: ${card.nomeEmpresa}</p>
        <p class="cardP">Receita: R$${card.receita}</p>
        <p class="cardP">Total: R$${card.total}</p>
    `;

            return novoItem;
        }

        checkTokenExpiration();
    }

});

function enviarDadosParaBackend(event) {
    event.preventDefault();

    var token = sessionStorage.getItem('token'); // Recuperando o token do sessionStorage

    if (token == null) {
        alert("Faça login para realizar uma operação")
        throw new Error('Usúario não logado.');
    }

    var nome = document.getElementById('nome').value;
    var valor = document.getElementById('valor').value;
    var resumo = document.getElementById('resumo').value;
    var data = document.getElementById('data').value;
    var categoria = document.getElementById('categoria').value;

    var partesDaData = data.split("-");
    var ano = parseInt(partesDaData[0]);
    var mes = parseInt(partesDaData[1]) - 1;
    var dia = parseInt(partesDaData[2]);

    var dataParaExtracao = new Date(ano, mes, dia);
    var dataNumero = dataParaExtracao.getMonth() + 1;

    var dados = {
        nomeCompra: nome,
        valorCompra: valor,
        descricao: resumo,
        date: data,
        category: categoria,
        incomeId: dataNumero
    };

    // Enviando os dados para o backend usando fetch
    fetch('https://backend-production-4f9d.up.railway.app/api/expense', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token // Adicionando o token JWT ao cabeçalho de autorização
        },
        body: JSON.stringify(dados)
    })
        .then(response => {
            if (response.ok) {
                // Se a resposta do servidor for bem-sucedida
                console.log('Dados enviados com sucesso!');
                alert("Dados enviados com sucesso!")
                limparCamposDoFormulario()
            } else {
                // Se a resposta do servidor não for bem-sucedida
                console.error('Erro ao enviar os dados:', response.statusText);
                alert("Erro ao enviar os dados")
                limparCamposDoFormulario()
            }
        })
        .catch(error => {
            console.error('Erro ao enviar os dados:', error);
        });
}

function limparCamposDoFormulario() {
    // Obtendo o formulário
    var formulario = document.getElementById('formInput'); // Substitua 'idDoSeuFormulario' pelo ID do seu formulário

    // Iterando sobre os elementos do formulário
    for (var i = 0; i < formulario.elements.length; i++) {
        var elemento = formulario.elements[i];

        // Verificando se o elemento é um campo de entrada (input) ou uma área de texto (textarea)
        if (elemento.tagName === 'INPUT' || elemento.tagName === 'TEXTAREA') {
            // Limpando o valor do elemento
            elemento.value = '';
        }
    }
}

function redirecionarParaListAll() {
    var token = sessionStorage.getItem('token'); // Recupera o token do sessionStorage

    if (token == null) {
        alert("Faça login para realizar uma operação");
        throw new Error('Usuário não logado.');
    } else {
        window.location.href = "listAllPage.html";
    }
}


function redirecionarParaListMonth() {
    var token = sessionStorage.getItem('token'); // Recupera o token do sessionStorage

    if (token == null) {
        alert("Faça login para realizar uma operação")
        throw new Error('Usúario não logado.');
    }

    window.location.href = "listMonthPage.html";
}

function redirect(idSection) {
    window.location.href = "#" + idSection;
    // Remove a classe 'selected' de todos os botões
    var buttons = document.querySelectorAll('.main-left button');
    buttons.forEach(function (button) {
        button.classList.remove('selected');
    });
    // Adiciona a classe 'selected' ao botão clicado
    var selectedButton = document.getElementById('btn' + idSection.charAt(0).toUpperCase() + idSection.slice(1));
    if (selectedButton) {
        selectedButton.classList.add('selected');
    }
}

function buttonLoginOut() {
    sessionStorage.clear();

    window.location.href = '../index.html';
}

function buttonLogin() {
    sessionStorage.clear();

    window.location.href = '../index.html';
}

function buttonRegister() {

    window.location.href = '../html/register.html';

}

function checkTokenExpiration() {
    var token = sessionStorage.getItem('token');
    var tokenTimestamp = sessionStorage.getItem('tokenTimestamp');

    if (token && tokenTimestamp) {
        var now = new Date().getTime();
        var twoHoursInMillis = 2 * 60 * 60 * 1000;
        var tokenAge = now - tokenTimestamp;

        if (tokenAge > twoHoursInMillis) {
            sessionStorage.clear();
            alert("Sua sessão expirou. Por favor, faça login novamente.");
            window.location.href = 'index.html'; // Redireciona para a página de login
        }
    }
}


