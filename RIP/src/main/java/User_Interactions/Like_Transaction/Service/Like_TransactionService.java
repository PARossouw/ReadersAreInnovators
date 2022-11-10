package User_Interactions.Like_Transaction.Service;

import Story.Model.Story;


public interface Like_TransactionService {
    
    //returns the total amount of likes
    Integer likeStory(Story story);
    

}