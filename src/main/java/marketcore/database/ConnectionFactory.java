package marketcore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = System.getenv("MARKETCORE_DB_URL");

    private static final String USUARIO = System.getenv("MARKETCORE_DB_USER");

    private static final String SENHA = System.getenv("MARKETCORE_DB_PASSWORD");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    URL,
                    USUARIO,
                    SENHA
            );
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao conectar com o banco de dados",
                    e
            );
        }
    }
}