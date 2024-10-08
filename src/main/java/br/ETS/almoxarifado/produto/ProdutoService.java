package br.ETS.almoxarifado.produto;

import br.ETS.almoxarifado.RegraDaAplicacaoException;  // Exceção personalizada usada para validar regras de negócio.
import br.ETS.almoxarifado.connection.ConnectionFactory;  // Classe responsável por criar e fornecer conexões com o banco de dados.

import java.sql.Connection;
import java.util.ArrayList;

public class ProdutoService {
    // Lista local que armazena todos os produtos carregados do banco de dados.
    private ArrayList<Produto> produtos = new ArrayList<>();

    // Objeto responsável por criar conexões com o banco de dados.
    private ConnectionFactory connectionFactory;

    // Construtor da classe ProdutoService. Inicializa a lista de produtos a partir do banco de dados.
    public ProdutoService() {
        this.connectionFactory = new ConnectionFactory();  // Cria uma nova fábrica de conexões.
        Connection connection = connectionFactory.recuperarConexao();  // Recupera uma conexão com o banco.
        this.produtos = new ProdutoDAO(connection).listar();  // Carrega todos os produtos do banco e os armazena na lista local.
    }

    // Método responsável por adicionar um novo produto ao sistema.
    public void adicionarNovoProduto(DadosProduto dadosProduto) {
        var produto = new Produto(dadosProduto);  // Cria um novo objeto Produto a partir dos dados fornecidos.

        // Verifica se o produto já existe na lista. Caso exista, lança uma exceção.
        if (produtos.contains(produto)) {
            throw new RegraDaAplicacaoException("Já existe um produto com esse ID");
        }

        Connection connection = connectionFactory.recuperarConexao();  // Abre uma nova conexão com o banco.
        new ProdutoDAO(connection).salvar(dadosProduto);  // Salva o novo produto no banco de dados.
    }

    // Método para exibir todos os produtos atualmente cadastrados no almoxarifado.
    public ArrayList<Produto> exibirProdutosAlmoxarifado() {
        Connection connection = connectionFactory.recuperarConexao();  // Abre uma nova conexão com o banco.
        return new ProdutoDAO(connection).listar();  // Retorna a lista de produtos do banco de dados.
    }

    // Método que busca um produto pelo seu ID.
    public Produto encontrarProdutoPeloID(int id) {
        Connection connection = connectionFactory.recuperarConexao();  // Abre uma nova conexão com o banco.
        Produto produto = new ProdutoDAO(connection).listarPorID(id);  // Procura o produto no banco de dados pelo ID.

        // Se o produto for encontrado, ele é retornado, caso contrário, lança uma exceção.
        if (produto != null) {
            return produto;
        } else {
            throw new RegraDaAplicacaoException("Não existe produto com esse ID");
        }
    }

    // Método responsável por adicionar uma quantidade ao estoque de um produto existente.
    public void adicionarQuantidadeDeUmProduto(int id, int quantidadeASerAdicionada) {
        var produto = encontrarProdutoPeloID(id);  // Busca o produto pelo ID.

        // Verifica se a quantidade a ser adicionada é válida (maior que 0).
        if (quantidadeASerAdicionada <= 0) {
            throw new RegraDaAplicacaoException("Quantidade a ser adicionada deve ser maior que 0.");
        }

        Connection connection = connectionFactory.recuperarConexao();  // Abre uma nova conexão com o banco.
        // Atualiza a quantidade do produto no banco de dados.
        new ProdutoDAO(connection).alterar(produto.getId(), produto.getQuantidade() + quantidadeASerAdicionada);
    }

    // Método responsável por remover uma quantidade do estoque de um produto existente.
    public void removerQuantidadeDeUmProduto(int id, int quantidadeASerRemovida) {
        var produto = encontrarProdutoPeloID(id);  // Busca o produto pelo ID.

        // Verifica se a quantidade a ser removida é válida (maior que 0).
        if (quantidadeASerRemovida <= 0) {
            throw new RegraDaAplicacaoException("Quantidade a ser removida deve ser maior que 0.");
        }

        // Verifica se a quantidade a ser removida não excede o estoque atual do produto.
        if (quantidadeASerRemovida > produto.getQuantidade()) {
            throw new RegraDaAplicacaoException("Quantidade a ser removida deve ser igual ou menor ao número no estoque.");
        }

        Connection connection = connectionFactory.recuperarConexao();  // Abre uma nova conexão com o banco.
        // Atualiza a quantidade do produto no banco de dados, subtraindo a quantidade removida.
        new ProdutoDAO(connection).alterar(produto.getId(), produto.getQuantidade() - quantidadeASerRemovida);
    }

    // Método responsável por remover um produto do almoxarifado.
    public void removerProdutoAlmoxarifado(int id) {
        if(encontrarProdutoPeloID(id) != null){
            Connection connection = connectionFactory.recuperarConexao();
            new ProdutoDAO(connection).deletar(id);
        } else {
            throw new RegraDaAplicacaoException("Não foi encontrado produto com esse ID.");
        }


    }
}
