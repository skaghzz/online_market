package term;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

private static String username = "postgres";
private static String password = "1234";
private static String url = "jdbc:postgresql://localhost:5432/termDB";
	
private static Connection conn = null;

public static Connection getConnection() throws Exception {
	try {
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(url, username, password);
	} catch (Exception e) {
		// TODO Auto-generated catch blocke.printStackTrace();
	System.out.println("dddddddddddddddddddddddddddddddddddddddddddd");
	}
	return conn;
}
}