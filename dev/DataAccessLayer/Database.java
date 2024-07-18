package DataAccessLayer;
import java.sql.*;

public class Database {
    public static final String DB_URL = "jdbc:sqlite:C:/Users/User/Desktop/לימודים/ניתוצ/עבודה 0/ADSS_Group_E/identifier.sqlite";
    public static Connection connect() throws SQLException {
            return DriverManager.getConnection(DB_URL);

        }


}

