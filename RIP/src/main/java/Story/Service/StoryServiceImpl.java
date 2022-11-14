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
        
        if(categories == null)
        {
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
        if(writer == null)
        {
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
        String successMessage = "";

        if (story == null) {
            successMessage = "The story is empty and could not be saved.";
        } else {

            try {
                storySuccessfullySaved = storyRepo.updateStory(story);

                if (storySuccessfullySaved) {
                    successMessage = "Story has been successfully saved.";
                } else {
                    successMessage = "unfotunetely, the story has not been saved successfully.";
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
        try {
            Story draft = storyRepo.retrieveStory(story);
            if (draft.getIsDraft() == false) {
                return null;
            } else {
                return draft;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Story> searchForStory(String storyParameter) {
        
        List<Story> allStoriesLike = new ArrayList<>();
        
        if (storyParameter.isBlank()) {
            return null;
        }
        
        try {
            if (storyRepo.searchForStory(storyParameter) != null) {
                allStoriesLike = storyRepo.searchForStory(storyParameter);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allStoriesLike;
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
