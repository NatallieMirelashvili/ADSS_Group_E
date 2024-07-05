package DataAccessLayer;
import java.sql.*;

public class Database {
    public static final String DB_URL = "jdbc:sqlite:identifier.db";
    public static Connection connect() throws SQLException {
            return DriverManager.getConnection(DB_URL);
        }


}

