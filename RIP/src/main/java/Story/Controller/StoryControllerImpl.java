package Story.Controller;

import Category.Model.Category;
import Story.Model.Story;
import Story.Service.StoryService;
import User.Model.Writer;
import java.util.List;

public class StoryControllerImpl implements StoryController {

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
    public String submitCompletedStory(Story story) {
        return storyService.submitCompletedStory(story);
    }

    @Override
    public Story viewSubmittedStory(Story story) {
        return storyService.viewSubmittedStory(story);
    }

    @Override
    public Story viewDraft(Story story) {
        return storyService.viewDraft(story);
    }

    @Override
    public List<Story> searchForStory(String StoryParameter) {
        return storyService.searchForStory(StoryParameter);
    }

    @Override
    public Story viewStoryDescription(Story story) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
