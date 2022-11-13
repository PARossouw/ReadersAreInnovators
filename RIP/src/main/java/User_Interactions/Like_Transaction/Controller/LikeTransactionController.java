package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import java.util.Calendar;
import java.util.Map;

public interface LikeTransactionController {
    
    Integer likeStory(Reader reader, Story story);
    
    String changeLike(Reader reader, Story story);
    
    Map<Story, Integer> getAllLikesInPeriod(Calendar startDate, Calendar endDate);
    
}
