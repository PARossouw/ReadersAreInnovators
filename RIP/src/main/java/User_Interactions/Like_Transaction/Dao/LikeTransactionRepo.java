package User_Interactions.Like_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author piete
 */
public interface LikeTransactionRepo {
    
    boolean createLike(Reader reader, Story story) throws SQLException;
    
    boolean updateLike(Reader reader, Story story) throws SQLException;
    
    Map<Story, Integer> getAllLikesInPeriod(Calendar startDate, Calendar endDate) throws SQLException;
    
}