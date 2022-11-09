package User_Interactions.View_Transaction.Dao;

import java.sql.SQLException;

/**
 *
 * @author piete
 */
public interface ViewTransactionRepo {
    
    boolean createView() throws SQLException;
    
}
