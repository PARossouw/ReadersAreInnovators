
package User_Interactions.Rating_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Service.RatingTransactionService;


public class RatingTransactionControllerImpl implements RatingTransactionController {

    private RatingTransactionService rating_transaction_service;
    
    @Override
    public Double rateStory(Story story, Reader reader, Integer rating) {
    
    return rating_transaction_service.rateStory(story, reader, rating);
    
    }
    
}
