package Story.Controller;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.Writer;
import java.util.List;

public interface StoryController {

    List<Story> searchStoriesByCategories(List<Category> categories);

    List<Story> viewStoriesByWriter(Writer writer);

    String saveStory(Story story);

    String submitCompletedStory(Story story);

    Story viewSubmittedStory(Story story);

    Story viewDraft(Story story);

    List<Story> searchForStory(String StoryParameter);

    Story viewStoryDescription(Story story);

}
