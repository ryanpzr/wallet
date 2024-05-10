function enviarDadosParaBackend() {
    // Obtendo o token JWT
    var token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ3YWxsZXR3aXphcmRfc2VydmljZSIsInN1YiI6InJ5YW5wZXJlaXJhbGltYWRzQGdtYWlsLmNvbSIsImV4cCI6MTcxNTMyOTUwNn0.pwWsiLS-xV6GU5q8iYWc9d7142ATmRgfRv-a1vhCNAk';

    // Obtendo os valores dos campos do formulário
    var nome = document.getElementById('nome').value;
    var valor = document.getElementById('valor').value;
    var resumo = document.getElementById('resumo').value;
    var data = document.getElementById('data').value;
    var categoria = document.getElementById('categoria').value;

    var dataParaExtracao = new Date(data);
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
            } else {
                // Se a resposta do servidor não for bem-sucedida
                console.error('Erro ao enviar os dados:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Erro ao enviar os dados:', error

            );
        });
}