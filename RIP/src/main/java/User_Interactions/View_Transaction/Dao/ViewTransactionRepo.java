package User_Interactions.View_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.Map;

public interface ViewTransactionRepo {
    
    Boolean createView(Story story, Reader reader) throws SQLException;
    
    Map<String, Integer> getAllStoryViewsInPeriod(String startDate, String endDate) throws SQLException;
    
}