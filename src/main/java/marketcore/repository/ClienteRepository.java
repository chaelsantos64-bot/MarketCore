package marketcore.repository;

import marketcore.cliente.Cliente;
import marketcore.database.ConnectionFactory;
import marketcore.exception.ClienteNaoEncontradoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public void cadastrarCliente(Cliente cliente) {
        String sql = """
                    INSERT INTO cliente (id,nome,email)
                    VALUES (?,?,?)
                """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, cliente.getId());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cliente ao Banco de Dados", e);
        }
    }

    public Cliente buscarCliente(long id) {
        String sql = """
                    SELECT * FROM cliente  WHERE id=?;
        """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long idBanco = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");

                return new Cliente(idBanco, nome, email);
            }
            throw new ClienteNaoEncontradoException("Cliente nao encontrado. ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao Buscar cliente ao Banco de Dados", e);
        }
    }

    public void atualizarCliente(Cliente cliente) {
        String sql = """
                    UPDATE cliente
                    SET nome=?,email=? WHERE id=?;
        """;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setLong(3, cliente.getId());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new ClienteNaoEncontradoException("Cliente nao encontrado. ID: " + cliente.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente ao Banco de Dados", e);
        }
    }

    public void excluirCliente(long id) {
        String sql = """
                    DELETE FROM cliente  WHERE id=?;
        """;
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, id);

            int linhasExcluidas = statement.executeUpdate();
            if (linhasExcluidas == 0) {
                throw new ClienteNaoEncontradoException("Cliente nao encontrado. ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente ao Banco de Dados", e);
        }
    }


    public List<Cliente> listarTodosClientes() {
        String sql = """
                    SELECT * FROM cliente
        """;
        List<Cliente> clientes = new ArrayList<>();

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");

                Cliente cliente = new Cliente(id, nome, email);
                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes ao Banco de Dados", e);
        }
    }
}



