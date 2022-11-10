package Story.Service;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import User.Model.Writer;
import java.util.List;


public interface StoryService {
    
    List<Story> searchStoriesByCategories(List<Category> categories);
    
    Boolean editPendingStoryByEditor(Story story);
    
    List<Story> viewWriterStories(Writer writer);
    
    Boolean saveDraftByWriter(Story story, Writer writer);
    
    Boolean submitCompletedAtory(Story story);
    
    Story viewSubmittedStory(Story story, Writer writer);
    
    Story viewDraft(Story story);
    
    List<Story> searchForStory(String storyParameter);
    
    Story viewStoryDescription(Story story);
    
    
    
    
    
    
    
    
    
    
    
    

}