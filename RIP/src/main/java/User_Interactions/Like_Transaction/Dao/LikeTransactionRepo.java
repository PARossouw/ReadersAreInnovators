package User_Interactions.Like_Transaction.Dao;

import java.sql.SQLException;

/**
 *
 * @author piete
 */
public interface LikeTransactionRepo {
    
    boolean createLike() throws SQLException;
    
    boolean updateLike() throws SQLException;
    
}