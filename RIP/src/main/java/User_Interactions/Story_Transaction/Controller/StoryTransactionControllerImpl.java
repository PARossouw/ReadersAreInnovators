package User_Interactions.Story_Transaction.Controller;

import Story.Model.Story;
import User.Model.Editor;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Service.Story_TransactionService;

public class StoryTransactionControllerImpl implements StoryTransactionController {
    
    private Story_TransactionService storyService;

    @Override
    public String approvePendingStory(Editor editor, Story story) {
        return storyService.approvePendingStory(editor, story);
    }

    @Override
    public String rejectPendingStory(Editor editor, Story story) {
        return storyService.rejectPendingStory(editor, story);
    }

    @Override
    public String removeStoryByWriter(Writer writer, Story story) {
        return storyService.removeStoryByWriter(writer, story);
    }
    
}
