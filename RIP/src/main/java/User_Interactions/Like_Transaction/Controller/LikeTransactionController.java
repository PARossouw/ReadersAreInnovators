package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import java.util.Calendar;
import java.util.Map;

public interface LikeTransactionController {
    
    String likeStory(Reader reader, Story story);
    
    String changeLike(Reader reader, Story story);
    
    Map<String, Integer> getAllLikesInPeriod(Calendar month);
    
}
