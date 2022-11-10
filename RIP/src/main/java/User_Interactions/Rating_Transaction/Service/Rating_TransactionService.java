package User_Interactions.Rating_Transaction.Service;

import Story.Model.Story;
import User.Model.User;


public interface Rating_TransactionService {
    
    Double rateStory(Story story, User user, Integer rating);

}