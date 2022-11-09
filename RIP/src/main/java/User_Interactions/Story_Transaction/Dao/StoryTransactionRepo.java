package User_Interactions.Story_Transaction.Dao;

import java.sql.SQLException;

/**
 *
 * @author piete
 */
public interface StoryTransactionRepo {

    boolean createEvent() throws SQLException;

}
