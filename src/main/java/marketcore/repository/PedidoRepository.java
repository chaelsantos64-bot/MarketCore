package marketcore.repository;

import marketcore.cliente.Cliente;
import marketcore.database.ConnectionFactory;
import marketcore.exception.PedidoNaoEncontradoException;
import marketcore.pedido.Pedido;
import marketcore.pedido.StatusPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    private ClienteRepository clienteRepository = new ClienteRepository();

    public void cadastrarPedido(Pedido pedido) {

        String sql = """
                INSERT INTO pedido
                (id, cliente_id, status_pedido, valor_total, data_pedido)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, pedido.getId());
            statement.setLong(2, pedido.getCliente().getId());
            statement.setString(3, pedido.getStatus().name());
            statement.setDouble(4, pedido.getValorTotal());
            statement.setObject(5, pedido.getData());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao cadastrar pedido no banco de dados.",
                    e
            );
        }
    }

    public Pedido buscarPedido(String id) {

        String sql = """
                SELECT * FROM pedido
                WHERE id = ?
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String idBanco = resultSet.getString("id");

                Long clienteId = resultSet.getLong("cliente_id");

                Cliente cliente =
                        clienteRepository.buscarCliente(clienteId);

                String statusBanco =
                        resultSet.getString("status_pedido");

                StatusPedido status =
                        StatusPedido.valueOf(statusBanco);

                double valorTotal =
                        resultSet.getDouble("valor_total");

                LocalDateTime dataPedido =
                        resultSet
                                .getTimestamp("data_pedido")
                                .toLocalDateTime();

                return new Pedido(
                        cliente,
                        status,
                        idBanco,
                        valorTotal,
                        dataPedido
                );
            }

            throw new PedidoNaoEncontradoException(
                    "Pedido não encontrado. ID: " + id
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar pedido no banco de dados.",
                    e
            );
        }
    }

    public void atualizarPedido(Pedido pedido) {
        String sql = """
                UPDATE pedido 
                SET cliente_id = ?,  status_pedido = ?, valor_total = ?, data_pedido = ?
                WHERE id = ?
        """;

        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ){
            statement.setLong(1,pedido.getCliente().getId());
            statement.setString(2,pedido.getStatus().name());
            statement.setDouble(3,pedido.getValorTotal());
            statement.setObject(4, pedido.getData());
            statement.setString(5, pedido.getId());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new PedidoNaoEncontradoException(
                        "Pedido não  encontrado. ID: " + pedido.getId()
                );
            }

        }catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao atualizar pedido no banco de dados.",
                    e
            );
        }
    }

    public List<Pedido> listarTodosPedidos() {
        String sql = """
                SELECT * FROM pedido
        """;
        List<Pedido> pedidos = new ArrayList<>();

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()){
                String idBanco = resultSet.getString("id");

                Long clienteId = resultSet.getLong("cliente_id");

                Cliente cliente =
                        clienteRepository.buscarCliente(clienteId);

                String statusBanco =
                        resultSet.getString("status_pedido");

                StatusPedido status_pedido =
                        StatusPedido.valueOf(statusBanco);

                double valorTotal =
                        resultSet.getDouble("valor_total");

                LocalDateTime dataPedido =
                        resultSet
                                .getTimestamp("data_pedido")
                                .toLocalDateTime();

                Pedido pedido = new Pedido(
                        cliente,
                        status_pedido,
                        idBanco,
                        valorTotal,
                        dataPedido
                );

                pedidos.add(pedido);
            }

            return pedidos;
        }catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao listar pedidos no Banco de dados.",
                    e
            );
        }
    }

    public void excluirPedido(String id) {
        String sql = """
                DELETE FROM pedido WHERE id = ?
        """;

        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,id);

            int linhasExcluidas = statement.executeUpdate();

            if (linhasExcluidas == 0) {
                throw new PedidoNaoEncontradoException(
                        "Pedido nao encontrado. ID:" + id
                );
            }
        }catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao excluir pedido do banco de dados.",
                    e
            );
        }
    }
}
