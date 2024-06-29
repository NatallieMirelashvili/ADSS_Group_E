package DataAccessLayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ItemAccessObj {
    private static final String DATABASE_URL = "jdbc:sqlite:SuperMarket.db";

    public Connection connect() {
        Connection connection = null;
        try {
            // Load the SQLite JDBC driver (you don't need to do this explicitly with the latest drivers)
            Class.forName("org.sqlite.JDBC");
            // Establish a connection to the database
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Item (" +
                "id INTEGER PRIMARY KEY," +
                "expirationDate DATE NOT NULL," +
                "place TEXT NOT NULL," +
                "StoreOrWare TEXT NOT NULL," +
                "status INTEGER NOT NULL)";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        ItemAccessObj dao = new ItemAccessObj();
        dao.createTable();
    }
}
