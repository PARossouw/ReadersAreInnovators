
package User_Interactions.Rating_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Service.Rating_TransactionService;


public class RatingTransactionControllerImpl implements RatingTransactionController {

    private Rating_TransactionService rating_transaction_service;
    
    @Override
    public Double rateStory(Story story, Reader reader, Integer rating) {
    
    return rating_transaction_service.rateStory(story, reader, rating);
    
    }

    @Override
    public String changeRating(Story story, Reader reader, Integer rating) {
    
    return rating_transaction_service.updateRating(story, reader, rating);
    }
    
}
