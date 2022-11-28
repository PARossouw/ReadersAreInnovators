package Story.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.Reader;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoryServiceImpl implements StoryService {

    private final StoryRepo storyRepo;
    private final CategoryRepo categoryRepo;

    public StoryServiceImpl(StoryRepo storyRepo, CategoryRepo categoryRepo) {
        this.storyRepo = storyRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Story> searchStoriesByCategories(List<Category> categories) {

        List<Story> storyList = new ArrayList<>();

        if (categories == null) {
            return null;
        }

        try {
            storyList = storyRepo.getStoryByCategory(categories);
            for (Story s : storyList) {
                s.setCategoryList(categoryRepo.getStoryCategories(s));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

    @Override
    public List<Story> viewStoriesByWriter(Writer writer) {

        List<Story> storyList = new ArrayList<>();
        if (writer == null) {
            return null;
        }

        try {
            storyList = storyRepo.getWriterStories(writer);
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

    @Override
    public String saveStory(Story story) {

        Boolean storySuccessfullySaved = false;

        if (story == null) {
            return "The story is empty and could not be saved.";
        } else {
            try {
                storySuccessfullySaved = storyRepo.updateStory(story);

                if (storySuccessfullySaved) {
                    return "Story has been successfully saved.";
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Unfortunetely, the story has not been saved successfully.";
    }

    @Override
    public String submitCompletedStory(Story story) {

        try {
            if (story.getStoryID() != null
                    && story.getTitle() != null
                    && story.getWriter() != null
                    && story.getDescription() != null
                    && story.getImagePath() != null
                    && story.getBody() != null
                    && story.getCreatedOn() != null
                    && story.getCategoryList() != null) {
                return storyRepo.submitStory(story) ? "successfully submitted story" : "unsuccessful operation";

            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Unsuccessful operation";
    }

    @Override
    public Story retrieveStory(Story story) {
        try {
            return storyRepo.retrieveStory(story);

        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Story> searchForStory(String storyParameter) {

        if (storyParameter.isBlank()) {
            return null;
        }

        try {

            return storyRepo.searchForStory(storyParameter);

        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Story> getLikedStory(Reader reader) {

        List<Story> likedStories = new ArrayList<>();
        if (reader == null) {
            return null;
        }

        try {
            likedStories = storyRepo.getLikedStories(reader);
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return likedStories;
    }

    @Override
    public List<Story> getFiveStoriesForStoryOfTheDay() {
        List<Story> stories = new ArrayList<>();
        try {
            return storyRepo.getFiveStoriesForStoryOfTheDay();
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stories;
    }

    @Override
    public List<Story> getTop20RatedStoriesOfTheMonth() {
         List<Story> stories = new ArrayList<>();
        try {
            stories = storyRepo.getHighestRatedStoriesForMonth();
            if(stories.size()>20){
                for(int i = 20; i<stories.size(); i++){
                    stories.remove(i);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stories;
    }

}
