import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysql {
    private final static String host = "localhost";
    private final static String nameDB = "studs";
    private final static String managerName = "postgres";
    private final static String managerPass = "s336262";
    private final static String linkDB = "jdbc:postgresql://" + host + ":5432/" + nameDB;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection co = DriverManager.getConnection(linkDB,managerName,managerPass)){
            System.out.println(co);
        }

    }
}
