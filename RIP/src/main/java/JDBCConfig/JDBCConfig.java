package JDBCConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConfig {

    private Connection connection;
    protected PreparedStatement ps;
    protected ResultSet rs;
    protected Integer rowsAffected;

    public JDBCConfig() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = "jdbc:mysql://localhost:3306/blonk";

        try {
            connection = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {

        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }

    }
    
    public void closeConnection()throws SQLException {
        
        if(connection != null){
            connection.close();
        }
        
    }

}
