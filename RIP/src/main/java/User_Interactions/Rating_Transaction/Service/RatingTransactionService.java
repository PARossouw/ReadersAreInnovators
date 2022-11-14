package User_Interactions.Rating_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;

public interface RatingTransactionService {
    
    Double rateStory(Story story, Reader reader, Integer rating);
    
}