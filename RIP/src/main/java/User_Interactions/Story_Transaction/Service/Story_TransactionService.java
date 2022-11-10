package User_Interactions.Story_Transaction.Service;

import Story.Model.Story;
import User.Model.Editor;


public interface Story_TransactionService {
    
    
    //remember to add a trigger to set is Approved to true on story
    Boolean approvePendingStory(Editor editor, Story story);
    
    Boolean rejectPendingStory(Editor editor, Story story);

}