package User_Interactions.Rating_Transaction.Dao;

import java.sql.SQLException;

/**
 *
 * @author piete
 */
public interface RatingTransactionRepo {
    
    boolean createRating() throws SQLException;
    
    boolean updateRating() throws SQLException;
    
}
