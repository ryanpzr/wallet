document.addEventListener('DOMContentLoaded', function () {

    const main = document.getElementById('listExpensePerMonth');

    document.getElementById('monthForm').addEventListener('submit', function (event) {
        event.preventDefault();

        carregarDadosReceita();
    });

    var token = sessionStorage.getItem('token');

    function carregarDadosReceita() {
        var nomeMes = document.getElementById("monthInput").value.toLowerCase();
        console.log(token)

        fetch(`https://backend-production-4f9d.up.railway.app/api/expense/listMonth/${nomeMes}`, {
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
