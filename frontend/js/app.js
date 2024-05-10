function enviarDadosParaBackend(event) {
    event.preventDefault(); // Evita o comportamento padrão do botão, que é enviar o formulário e recarregar a página

    try {
        // Obtendo o token JWT
        var token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ3YWxsZXR3aXphcmRfc2VydmljZSIsInN1YiI6InJ5YW5wZXJlaXJhbGltYWRzQGdtYWlsLmNvbSIsImV4cCI6MTcxNTM3ODUyNX0.r5Z39-GULubN4wlLtQQzJ5W_0e0b452qP0SIpq9x8fg';

        var nome = document.getElementById('nome').value;
        if (!nome) {
            alert("Nome não deve ser nulo.");
            return;
        } else if (!/^[a-zA-Z\s]+$/.test(nome)) {
            alert("Nome deve conter apenas letras.");
            return;
        }

        var valor = document.getElementById('valor').value;
        if (!valor) {
            alert("Valor não deve ser nulo.");
            return;
        } else if (!/^\d+$/.test(valor)) {
            alert("Valor deve conter apenas números.");
            return;
        }

        var resumo = document.getElementById('resumo').value;
        if (!resumo) {
            alert("Resumo não deve ser nulo.");
            return;
        } else if (!/^[a-zA-Z\s]+$/.test(resumo)) {
            alert("Resumo deve conter apenas letras.");
            return;
        }

        var data = document.getElementById('data').value;
        if (!data) {
            alert("Data não deve ser nula.");
            return;
        } else if (!/^\d{4}-\d{2}-\d{2}$/.test(data)) {
            alert("Data deve estar no formato (YYYY-MM-DD).");
            return;
        }


        var categoria = document.getElementById('categoria').value;

        var partesDaData = data.split("-");
        var ano = parseInt(partesDaData[0]);
        var mes = parseInt(partesDaData[1]) - 1;
        var dia = parseInt(partesDaData[2]);

        var dataParaExtracao = new Date(ano, mes, dia);
        var dataNumero = dataParaExtracao.getMonth() + 1;

        // Criando um objeto com os dados a serem enviados
        var dados = {
            nomeCompra: nome,
            valorCompra: valor,
            descricao: resumo,
            date: data,
            category: categoria,
            incomeId: dataNumero
        };

        // Enviando os dados para o backend usando fetch
        fetch('http://localhost:8080/api/expense', {
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
                console.error('Erro ao enviar os dados:', error

                );
            });



    } catch (error) {
        console.error('Erro ao enviar os dados para o backend:', error);

    }

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
    window.location.href = "listAll.html";
}

function redirecionarParaListMonth() {
    window.location.href = "listMonth.html";
}



