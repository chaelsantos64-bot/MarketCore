package marketcore.repository;


import marketcore.database.ConnectionFactory;
import marketcore.exception.PedidoNaoEncontradoException;
import marketcore.pedido.ItemPedido;
import marketcore.pedido.Pedido;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoRepository {
    public void cadastrarItemPedido(
            String id,
            String pedidoId,
            String produtoId,
            int quantidade,
            double precoUnitario
    ) {
        String sql = """
                INSERT INTO item_pedido
                (id, pedido_id, produto_id, quantidade, preco_unitario)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, id);
            statement.setString(2, pedidoId);
            statement.setString(3, produtoId);
            statement.setInt(4, quantidade);
            statement.setDouble(5, precoUnitario);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao cadastrar ItemPedido no banco de dados."
            );
        }
    }

    public List<ItemPedido> buscarItensPorPedido(String pedidoId) {

        String sql = """
                SELECT * FROM item_pedido
                WHERE  pedido_id = ?
                """;

        List<ItemPedido> itens = new ArrayList<>();

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, pedidoId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String idBanco = resultSet.getString("id");
                String pedidoIdBanco = resultSet.getString("pedido_id");
                String produtoId = resultSet.getString("produto_id");
                int quantidade = resultSet.getInt("quantidade");
                double precoUnitario =
                        resultSet.getDouble("preco_unitario");

                ItemPedido itemPedido = new ItemPedido(
                        idBanco,
                        pedidoIdBanco,
                        produtoId,
                        quantidade,
                        precoUnitario
                );

                itens.add(itemPedido);
            }

            return itens;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar itens do pedido no banco de dados.",
                    e
            );
        }
    }

    public void atualizarItemPedido(ItemPedido itemPedido) {
        String sql = """
                        UPDATE item_pedido 
                        SET pedido_id=?, 
                            produto_id=?,
                            quantidade=?,
                            preco_unitario=?
                        WHERE id = ?
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, itemPedido.getPedidoId());
            statement.setString(2, itemPedido.getProdutoId());
            statement.setInt(3, itemPedido.getQuantidade());
            statement.setDouble(4, itemPedido.getPrecoUnitario());
            statement.setString(5, itemPedido.getId());

            int linhasAlteradas = statement.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new PedidoNaoEncontradoException(
                        "Pedido não  encontrado. ID: " + itemPedido.getPedidoId()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar ItemPedido.",
                    e
            );
        }
    }

    public void excluirItensPorPedido(String pedidoId) {
        String sql = """
                        DELETE FROM item_pedido
                        WHERE pedido_id = ?
                """;
        List<ItemPedido> itens = new ArrayList<>();

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, pedidoId);

            int linhasExcluidas = statement.executeUpdate();

            if (linhasExcluidas == 0) {
                throw new PedidoNaoEncontradoException(
                        "Pedido nao encontrado. ID: " + pedidoId
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao excluir Pedido do Banco de dados", e
            );
        }
    }

    public void excluirItemPedido(String id ){
        String sql = """
                        DELETE FROM item_pedido
                        WHERE id = ?
        """;

        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ) {
            statement.setString(1, id);

            int linhasExcluidas = statement.executeUpdate();

            if (linhasExcluidas == 0) {
                throw new PedidoNaoEncontradoException(
                        "Pedido nao encontrado. ID: " + id
                );
            }
        }catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao excluir Pedido do Banco de dados", e
            );
        }

    }
}

