document.addEventListener('DOMContentLoaded', function () {
    const main = document.getElementById('listExpensePerMonth');

    document.getElementById('monthForm').addEventListener('submit', function (event) {
        event.preventDefault();

        carregarDadosReceita();
    });

    function carregarDadosReceita() {
        var nomeMes = document.getElementById("monthInput").value.toLowerCase();
        var token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ3YWxsZXR3aXphcmRfc2VydmljZSIsInN1YiI6InJ5YW5wZXJlaXJhbGltYWRzQGdtYWlsLmNvbSIsImV4cCI6MTcxNTgxMDI1N30.eqoYofr5T9B5HUjGhqm7QRAkq3v4jfXA3wnUawVib3I'

        fetch(`http://localhost:8080/api/expense/listMonth/${nomeMes}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // Adicionando o token JWT ao cabeçalho de autorização
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Erro ao buscar dados do servidor.');
                }
            })
            .then(data => {
                if (data.content && Array.isArray(data.content)) {
                    main.innerHTML = '';
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
        <p class="cardP">Compra: ${card.nomeCompra}</p>
        <p class="cardP">Valor: R$${card.valorCompra}</p>
        <p class="cardP">Descrição: ${card.descricao}</p>
        <p class="cardP">Data: ${card.date}</p>
        <p class="cardP">Categoria: ${card.category}</p>
    `;

        return novoItem;
    }
});
