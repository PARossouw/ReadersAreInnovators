package User_Interactions.Story_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;



public interface StoryTransactionRepo {

    boolean createEvent(Story story, Reader reader) throws SQLException;
    
    boolean updateEvent(Story story, String action, Reader reader) throws SQLException;
    
    
    
    

}
