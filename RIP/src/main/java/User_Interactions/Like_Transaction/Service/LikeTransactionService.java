package User_Interactions.Like_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import java.util.Calendar;
import java.util.Map;


public interface LikeTransactionService {
    
    String likeStory(Reader reader, Story story);
    
    Map<Story, Integer> getAllLikesInPeriod(Calendar startDate);
    
}