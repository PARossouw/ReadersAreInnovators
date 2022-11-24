package Story.Service;

import Category.Model.Category;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoryServiceImpl implements StoryService {

    private final StoryRepo storyRepo;

    public StoryServiceImpl(StoryRepo storyRepo) {
        this.storyRepo = storyRepo;
    }

    @Override
    public List<Story> searchStoriesByCategories(List<Category> categories) {

        List<Story> storyList = new ArrayList<>();

        if (categories == null) {
            return null;
        }

        try {
            storyList = storyRepo.getStoryByCategory(categories);
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
//        try {
//            return storyRepo.retrieveStory(story);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }

Story storyObj = new Story();
storyObj.setStoryID(420);
        storyObj.setTitle("DAO practice Title");
        storyObj.setAvgRating(2.9);
        storyObj.setWriter("DAO Pratice Author Tarun Sing");
        storyObj.setDescription("DAO Practice Description");
        storyObj.setBody("DAO Practice Body");

        return storyObj;
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

}
