package User_Interactions.Rating_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User.Model.User;
import java.sql.SQLException;


public interface Rating_TransactionService {
    
    Double rateStory(Story story, Reader reader, Integer rating);
    
    Boolean updateRating(Story story, Reader reader, Integer rating);

}