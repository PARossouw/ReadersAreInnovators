package User_Interactions.Like_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;


public interface Like_TransactionService {
    
    //returns the total amount of likes
    Integer likeStory(Reader reader, Story story);
    
    String changeLike(Reader reader, Story story);
    
    Map<Story, Integer> getAllLikesInPeriod(Calendar startDate);
    
    
    

}