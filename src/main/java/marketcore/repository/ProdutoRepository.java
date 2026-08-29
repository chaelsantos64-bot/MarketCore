package marketcore.repository;

import marketcore.database.ConnectionFactory;
import marketcore.exception.ProdutoNaoEncontradoException;
import marketcore.produto.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutoRepository {

    public void cadastrarProduto(Produto produto) {
        String sql = """
                INSERT INTO produto(id ,nome,quantidade,preco)
                VALUES(?, ?, ?, ?)
                """;
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, produto.getId());
            statement.setString(2, produto.getNome());
            statement.setInt(3, produto.getQuantidade());
            statement.setDouble(4, produto.getPreco());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar produto no banco", e);
        }
    }

    public Produto buscarProduto(String id) {
        String sql = """
                SELECT * FROM produto WHERE id = ?;
        """;
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idBanco = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                int quantidade = resultSet.getInt("quantidade");
                double preco = resultSet.getDouble("preco");

                return new Produto(
                        nome,
                        idBanco,
                        quantidade,
                        preco
                );
            }
            throw new ProdutoNaoEncontradoException(
                    "Produto não encontrado. ID: " + id
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto no banco", e);
        }


    }

    public void atualizarProduto(Produto produto) {
        String sql = """
                UPDATE produto
                SET nome = ?, quantidade = ?, preco = ?
                WHERE id = ?;
        """;

        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,produto.getNome());
            statement.setInt(2,produto.getQuantidade());
            statement.setDouble(3,produto.getPreco());
            statement.setString(4, produto.getId());

            int linhasAlteradas = statement.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new ProdutoNaoEncontradoException(
                        "Produto não encontrado. ID: " + produto.getId()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto no banco", e);
        }
    }

    public void excluirProduto(String id) {
        String sql = """
                DELETE FROM produto WHERE id = ?;
        """;
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ){

            statement.setString(1,id);

            int linhasExcluidas = statement.executeUpdate();
            if (linhasExcluidas == 0) {
                throw new ProdutoNaoEncontradoException(
                        "Produto não encontrado. ID: " + id
                );
            }
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto no banco", e);
        }
    }

    public List<Produto> listarTodosProdutos() {
        String sql = """
                SELECT * FROM produto
                """;
        List<Produto> produtos = new ArrayList<>();

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()){
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                int quantidade = resultSet.getInt("quantidade");
                double preco = resultSet.getDouble("preco");

                Produto produto = new Produto(
                        nome,
                        id,
                        quantidade,
                        preco
                );

                produtos.add(produto);
            }
            return produtos;

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos no banco", e);
        }
    }
}
