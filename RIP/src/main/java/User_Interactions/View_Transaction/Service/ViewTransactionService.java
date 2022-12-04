package User_Interactions.View_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public interface ViewTransactionService {
    
    String viewStory(Story story, Reader reader);
    
    Map<String, Integer> getAllStoryViewsInPeriod(String startDate, String endDate);

}