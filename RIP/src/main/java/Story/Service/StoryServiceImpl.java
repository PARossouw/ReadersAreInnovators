package Story.Service;

import Category.Dao.CategoryRepo;
import Category.Model.Category;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.User;
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
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storyList;
    }

    @Override
    public String saveStory(Story story) {

        Boolean storySuccessfullySaved = false;
        Story storySaved = new Story();
        storySaved.setStoryID(-1);

        if (story == null) {
            return "The story is empty and could not be saved.";
        } else {
            try {

                if (story.getStoryID() == -1) {
                    
                    storySaved = storyRepo.createStory(story);
                    
                    //hardCoding
//                    Category cat = new Category();
//                    cat.setCategoryID(1);
//                    storySaved.getCategoryList().add(cat);
//                    
//                    storySaved.setCategoryList(story.getCategoryList());
                    for (int i = 0; i < story.getCategoryList().size(); i++) {
                        List<Category> catList = new ArrayList<>();
                        catList.add(story.getCategoryList().get(i));
                        
                          categoryRepo.addCategoriesToStory(storySaved, catList);
                    }
                  
                } else {
                    storySuccessfullySaved = storyRepo.updateStory(story);
                    
                    for (int i = 0; i < story.getCategoryList().size(); i++) {
                        List<Category> catList = new ArrayList<>();
                        catList.add(story.getCategoryList().get(i));
                        
                          categoryRepo.addCategoriesToStory(story, catList);
                    }
                    //categoryRepo.addCategoriesToStory(story, story.getCategoryList());
                }

                if (storySaved.getStoryID() != -1 ) {
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
    public List<Story> getLikedStory(User reader) {

        List<Story> likedStories = new ArrayList<>();
        if (reader.getUserID() == null) {
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
    public List<Story> getPendingStories() {

        List<Story> pendingStories = new ArrayList<>();

        try {
            pendingStories = storyRepo.getPendingStories();
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
            return storyRepo.getStoriesForStoryOfTheDay();
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
//        Story s = new Story();
//        s.setTitle("test title");
//        s.setBody("test body");
//        s.setIsDraft(false);
//        s.setStoryID(1);
//        s.setIsApproved(true);
//        return s;
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

}
