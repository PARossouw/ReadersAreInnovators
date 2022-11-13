package Story.Controller;

import Category.Model.Category;
import Story.Model.Story;
import Story.Service.StoryService;
import User.Model.Writer;
import java.util.List;


public class StoryControllerImpl implements StoryController{
    
    private StoryService storyService;

    @Override
    public List<Story> searchStoriesByCategories(List<Category> categories) {
        return storyService.searchStoriesByCategories(categories);
        
    }

    @Override
    public List<Story> viewStoriesByWriter(Writer writer) {
        return storyService.viewStoriesByWriter(writer);
    }

    @Override
    public String saveStory(Story story) {
        return storyService.saveStory(story);
    }

    @Override
    public Story viewStoryDescription(Story story) {
        return storyService.viewStoryDescription(story);
    }

   

}