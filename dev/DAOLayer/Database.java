package DAOLayer;
import java.sql.*;

public class Database {
    public static final String DB_URL = "jdbc:sqlite:C:\\Users\\natal\\OneDrive - post.bgu.ac.il\\שנה ב\\סמסטר ב\\ניתוח ותיכון ארנון\\ADSS_Group_E\\identifier.db";
    public static Connection connect() throws SQLException {
            return DriverManager.getConnection(DB_URL);

        }


}

