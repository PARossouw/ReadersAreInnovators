package User_Interactions.Like_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author piete
 */
public interface LikeTransactionRepo {
    
    boolean createLike(Reader reader, Story story) throws SQLException;
    
    boolean updateLike(Reader reader, Story story) throws SQLException;
    
    List<Story> getAllLikesInPeriod(Reader reader) throws SQLException;
    
}