package Story.Controller;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import java.util.List;

public interface StoryController {

    List<Story> searchStoriesByCategories(List<Category> categories);

    List<Story> viewStoriesByWriter(Writer writer);

    String saveStory(Story story);

    String submitCompletedStory(Story story);

    Story retrieveStory(Story story);

    List<Story> searchForStory(String StoryParameter);
    
    List<Story> viewLikedStories(User reader);

}
