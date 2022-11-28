package Story.Service;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.Reader;
import User.Model.Writer;
import java.util.List;

public interface StoryService {

    List<Story> searchStoriesByCategories(List<Category> categories);

    List<Story> viewStoriesByWriter(Writer writer);

    String saveStory(Story story);

    String submitCompletedStory(Story story);

    Story retrieveStory(Story story);

    List<Story> searchForStory(String storyParameter);
    
    List<Story> getLikedStory(Reader reader);

    List<Story> getFiveStoriesForStoryOfTheDay();

    List<Story> getTop20RatedStoriesOfTheMonth();
}
