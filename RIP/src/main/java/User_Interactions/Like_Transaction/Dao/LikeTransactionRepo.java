package User_Interactions.Like_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

public interface LikeTransactionRepo {
    
    Boolean createLike(Reader reader, Story story) throws SQLException;
    
    Boolean updateLike(Reader reader, Story story) throws SQLException;
    
    Boolean getLike(Reader reader, Story story) throws SQLException;
    
    Map<String, Integer> getAllLikesInPeriod(String month) throws SQLException;
    
}