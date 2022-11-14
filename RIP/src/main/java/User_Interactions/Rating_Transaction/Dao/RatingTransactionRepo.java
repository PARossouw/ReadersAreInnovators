package User_Interactions.Rating_Transaction.Dao;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Model.RatingTransaction;
import java.sql.SQLException;

public interface RatingTransactionRepo {

    Boolean createRating(Story story, Reader reader, int rating) throws SQLException;
    
    RatingTransaction getRating(Story story, Reader Reader) throws SQLException;

    Boolean updateRating(Story story, Reader reader, int rating) throws SQLException;

}
