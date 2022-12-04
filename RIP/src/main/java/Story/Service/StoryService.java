package Story.Service;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import java.util.List;
import java.util.Map;

public interface StoryService {

    List<Story> searchStoriesByCategories(List<Category> categories);

    List<Story> viewStoriesByWriter(User writer);

    String saveStory(Story story);

    String submitCompletedStory(Story story);

    Story retrieveStory(Story story);

    List<Story> searchForStory(String storyParameter);
    
    List<Story> getLikedStory(User reader);

    List<Story> getPendingStories();
    
    List<Story> getStoriesForStoryOfTheDay();


    Map<String, Integer> getTop20RatedStoriesOfTheMonth(String month);

   // List<Story> getTop20RatedStoriesOfTheMonth();
    
    List<Story> getRandomApprovedStories();


    String turnOffComments(Story story);
}
