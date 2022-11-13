package User_Interactions.Rating_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;

public interface RatingTransactionController {
    
    Double rateStory(Story story, Reader reader, Integer rating);
    
}
