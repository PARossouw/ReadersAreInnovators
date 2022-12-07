package User_Interactions.Story_Transaction.Controller;

import Story.Model.Story;
import User.Model.Editor;
import User.Model.Writer;

public interface StoryTransactionController {

    String approvePendingStory(Editor editor, Story story);

    String rejectPendingStory(Editor editor, Story story);

    String removeStoryByWriter(Writer writer, Story story);

}
