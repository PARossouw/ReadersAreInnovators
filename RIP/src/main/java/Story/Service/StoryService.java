package Story.Service;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.Writer;
import java.util.List;

public interface StoryService {

    List<Story> searchStoriesByCategories(List<Category> categories);

    List<Story> viewStroiesByWriter(Writer writer);

    String saveStory(Story story);

    String submitCompletedStory(Story story);

    Story viewSubmittedStory(Story story);

    Story viewDraft(Story story);

    List<Story> searchForStory(String storyParameter);

    Story viewStoryDescription(Story story);

}
