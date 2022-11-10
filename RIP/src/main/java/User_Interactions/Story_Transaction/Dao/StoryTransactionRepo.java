package User_Interactions.Story_Transaction.Dao;

import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;



public interface StoryTransactionRepo {

    boolean createEvent(Story story, User user, String action) throws SQLException;
    

    
    
    
    
    
    

}
