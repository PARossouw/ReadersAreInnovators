package User_Interactions.Rating_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author piete
 */
public interface RatingTransactionRepo {

    Boolean createRating(Story story, Reader reader, int rating) throws SQLException;

    Boolean updateRating(Story story, Reader reader, int rating) throws SQLException;

}
