package User_Interactions.View_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User.Model.User;

public interface ViewTransactionController {
    
    String viewStory(Story story, Reader reader);
    
}