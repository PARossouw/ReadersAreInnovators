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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoryServiceImpl implements StoryService {

    private final StoryRepo storyRepo;
    private final CategoryRepo categoryRepo;
    public static Story storyOfTheDay;

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

        Story storySaved = new Story();
        storySaved.setStoryID(-1);

        if (story == null) {
            return "The story is empty and could not be saved.";
        } else {
            try {
                if (story.getStoryID() == -1) {

                    storySaved = storyRepo.createStory(story);

                    for (int i = 0; i < story.getCategoryList().size(); i++) {
                        List<Category> catList = new ArrayList<>();
                        catList.add(story.getCategoryList().get(i));

                        categoryRepo.addCategoriesToStory(storySaved, catList);
                    }
                } else {
                    storyRepo.updateStory(story);

                    for (int i = 0; i < story.getCategoryList().size(); i++) {
                        List<Category> catList = new ArrayList<>();
                        catList.add(story.getCategoryList().get(i));

                        categoryRepo.addCategoriesToStory(story, catList);
                    }
                }
                if (storySaved.getStoryID() != -1) {
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
    public Map<String, Double> getTop20RatedStoriesOfTheMonth(String month) {
        Map<String, Double> stories = new HashMap<>();
        try {
            stories = storyRepo.getHighestRatedStoriesForMonth(month);
            
            if(stories.isEmpty() || stories == null){
                stories.put("no data for selected period", -1);
                return stories;
            }
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


    @Override
    public String makeStoryOfTheDay(Story story) {
        if (story != null) {
            this.storyOfTheDay = story;
            return " was made story of the day.";
        }
        return "...there was a problem saving the story of the day";
    }

    @Override
    public Story getStoryOfTheDay() {
        if (this.storyOfTheDay == null) {
            while (true) {
                Story story = new Story();
                story.setStoryID((int) ((Math.random() * 100000) + 1));

                story = retrieveStory(story);
                if (story.getIsApproved() != null && story.getIsApproved() == true && story.getIsActive() == true && story.getIsDraft() == false) {
                    this.storyOfTheDay = story;
                    return story;
                }
            }
        }
        return this.storyOfTheDay;
    }

    @Override
    public String blockStory(Story story) {
        try {
            return storyRepo.blockStory(story) ? "" + story.getTitle() + " removed from public view" : "Could not remove story from public view.";
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "something went wrong";
    }

    @Override
    public String incrementViews(Story story) {
        try {
            return storyRepo.incrementViews(story);
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "could not increment views";
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
