package User_Interactions.Story_Transaction.Service;

import SMS.smsreq;
import Story.Model.Story;
import User.Model.Editor;
import User.Model.Writer;


public interface Story_TransactionService {
    
    
    //remember to add a trigger to set is Approved to true on story
    String approvePendingStory(Editor editor, Story story);
    
    smsreq rejectPendingStory(Editor editor, Story story);
    
    String removeStoryByWriter(Writer writer, Story story);

}