package User_Interactions.View_Transaction.Controller;

import Story.Model.Story;
import User.Model.User;
import User_Interactions.View_Transaction.Service.ViewTransactionService;

public class ViewTransactionControllerImpl implements ViewTransactionController {

    private ViewTransactionService view_transaction_service ;
    
    @Override
    public String viewStory(Story story, User user) { 
    
        return view_transaction_service.viewStory(story, user);
    }
    
}
