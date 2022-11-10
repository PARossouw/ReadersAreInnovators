package User_Interactions.View_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

public interface ViewTransactionRepo {
    
    Boolean createView(Story story, Reader reader) throws SQLException;
    
    Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) throws SQLException;
    
}