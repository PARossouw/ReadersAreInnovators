package User_Interactions.Story_Transaction.Dao;

import Story.Model.Story;
import java.sql.SQLException;
import user.Reader.Model.Reader;

/**
 *
 * @author piete
 */
public interface StoryTransactionRepo {

    boolean createEvent(Story story, Reader reader) throws SQLException;
    
    boolean updateEvent(Story story, Boolean bool, Reader reader) throws SQLException;
    
    

}
