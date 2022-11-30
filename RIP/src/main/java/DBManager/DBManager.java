package DBManager;

import jakarta.activation.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

    private static DataSource dSource;
    private final static BasicDataSource dataSource;
    protected PreparedStatement ps;
    protected ResultSet rs;
    protected Integer rowsAffected;

    public DBManager() {
    }

    static {
        DBProperties prop = new DBProperties();
        
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(prop.readProperty("db.driver"));
        dataSource.setUrl(prop.readProperty("db.url"));
        dataSource.setUsername(prop.readProperty("db.user"));
        dataSource.setPassword(prop.readProperty("db.password"));
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() throws SQLException {

        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (getConnection() != null) {
            getConnection().close();
        }
    }
}