package Story.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.User;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
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
        try {
            storyList = storyRepo.getStoryByCategory(categories);
            for (Story story : storyList) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storyList;
    }

    @Override
    public List<Story> viewStoriesByWriter(User writer) {

        List<Story> storyList = new ArrayList<>();
        if (writer == null) {
            return null;
        }
        try {
            storyList = storyRepo.getWriterStories(writer);
            for (Story story : storyList) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
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

                if (story.getStoryID() == -1) {
                    storySuccessfullySaved = storyRepo.createStory(story);
                } else {
                    storySuccessfullySaved = storyRepo.updateStory(story);
                    categoryRepo.addCategoriesToStory(story, story.getCategoryList());
                }

                if (storySuccessfullySaved) {
                    return "Story has been successfully saved.";
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Unfortunetely, the story has not been saved successfully. " + story.toString();
    }

    @Override
    public String submitCompletedStory(Story story) {

        try {
            if (story.getStoryID() != null
                    && story.getTitle() != null
                    //  && story.getWriter() != null
                    && story.getDescription() != null
                    //  && story.getImagePath() != null
                    && story.getBody() != null) {
                //  && story.getCreatedOn() != null
                //   && story.getCategoryList() != null) {
                return storyRepo.submitStory(story) ? "successfully submitted story" : "unsuccessful operation";

            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Unsuccessful operation " + story.toString();
    }

    @Override
    public Story retrieveStory(Story story) {
        Story s = new Story();
        try {
            s = storyRepo.retrieveStory(story);
            s.setImagePath(getEncodedString(s.getImagePath()));
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Story> searchForStory(String storyParameter) {

        List<Story> stories = new ArrayList<>();
        if (storyParameter.isBlank()) {
            return null;
        }

        try {

            stories = storyRepo.searchForStory(storyParameter);
            for (Story story : stories) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<Story> getLikedStory(User reader) {

        List<Story> likedStories = new ArrayList<>();
        if (reader.getUserID() == null) {
            return null;
        }

        try {
            likedStories = storyRepo.getLikedStories(reader);
            for (Story story : likedStories) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return likedStories;
    }

    @Override
    public List<Story> getPendingStories() {

        List<Story> pendingStories = new ArrayList<>();

        try {
            pendingStories = storyRepo.getPendingStories();
            for (Story story : pendingStories) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
            return pendingStories;
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pendingStories;

    }

    @Override
    public List<Story> getStoriesForStoryOfTheDay() {

        List<Story> stories = new ArrayList<>();

        try {
            stories = storyRepo.getStoriesForStoryOfTheDay();
            for (Story story : stories) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
            return stories;
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stories;

    }

    @Override
    public Map<String, Integer> getTop20RatedStoriesOfTheMonth(String month) {
        Map<String, Integer> stories = new HashMap<>();
        try {
            stories = storyRepo.getHighestRatedStoriesForMonth(month);
            return stories;
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
            for (Story story : stories) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
            return stories;
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stories;

    }

    @Override
    public List<Story> getRandomApprovedStories() {
        List<Story> stories = new ArrayList<>();
        try {
            stories = storyRepo.getApprovedStories();
            for (Story story : stories) {
                story.setImagePath(getEncodedString(story.getImagePath()));
            }
            return stories;
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stories;
    }

    @Override
    public String turnOffComments(Story story) {

        try {
            if (story.getAllowComments()) {
                if (storyRepo.turnOffComments(story)) {
                    return "Comments for " + story.getTitle() + " successfully disabled";
                }
            }
            if (!story.getAllowComments()) {
                if (storyRepo.turnOffComments(story)) {
                    return "Comments for " + story.getTitle() + " enabled";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "something went wrong";

    }

    public String getEncodedString(String filePath) {

        try {
            byte[] data = Files.readAllBytes(java.nio.file.Path.of(filePath));
            String encodedImage = Base64.getMimeEncoder().encodeToString(data);
            return encodedImage;
        } catch (IOException ex) {
            return "Image could not be loaded";
        }

    }

}
