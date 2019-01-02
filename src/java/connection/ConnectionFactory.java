package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final String drive = "com.mysql.jdbc.Driver";
    //  private static final String url = "jdbc:mysql://localhost:3306/needful";
    // private static final String user = "root";
    //  private static final String password = "root";

    private static final String url = "jdbc:mysql://needfuldb.cytszgenicog.sa-east-1.rds.amazonaws.com:3306/needful";
    private static final String user = "rootneedful";
    private static final String password = "rootneedful";

    public static Connection getConnection() {

        try {

            Class.forName(drive);
            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conex�o", ex);
        }
    }

    public static void closeConnection(Connection con) {

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro:" + ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro:" + ex);
        }
    }

}
