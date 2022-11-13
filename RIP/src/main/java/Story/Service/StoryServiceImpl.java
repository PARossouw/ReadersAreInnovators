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

    private StoryRepo storyRepo;

    @Override
    public List<Story> searchStoriesByCategories(List<Category> categories) {

        List<Story> storyList = new ArrayList<>();
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
        String successMessage = "";

        if (story == null) {
            successMessage = "The story is empty and thus could not be saved.";
        } else {

            try {
                storySuccessfullySaved = storyRepo.updateStory(story);

                if (storySuccessfullySaved) {
                    successMessage = "Story has been successfully saved.";
                } else {
                    successMessage = "unfotunetly, the story has not been saved successfully.";
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return successMessage;
    }

    @Override
    public String submitCompletedStory(Story story) {
        boolean b = false;
        try {
            if (story.getStoryID() != null
                    && story.getTitle() != null
                    && story.getWriter() != null
                    && story.getDescription() != null
                    && story.getImagePath() != null
                    && story.getBody() != null
                    && story.getCreatedOn() != null
                    && story.getCategoryList() != null) {
                b = storyRepo.submitStory(story);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b ? "successfully submitted story" : "unsuccessful operation";
    }

    @Override
    public Story viewSubmittedStory(Story story) {
        Story s = null;
        try {
            s = storyRepo.retrieveStory(story);
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    @Override
    public Story viewDraft(Story story) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Story> searchForStory(String storyParameter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Story viewStoryDescription(Story story) {

        try {
            Story myStory = storyRepo.retrieveStory(story);

            if (myStory != null) {
                return myStory;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
