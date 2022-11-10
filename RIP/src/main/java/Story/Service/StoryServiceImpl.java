package Story.Service;

import Category.Model.Category;
import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StoryServiceImpl implements StoryService {
    private StoryRepo storyRepo;

    @Override
    public List<Story> searchStoriesByCategories(List<Category> categories) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean editPendingStoryByEditor(Story story) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Story> viewWriterStories(Writer writer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean saveDraftByWriter(Story story, Writer writer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean submitCompletedAtory(Story story) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Story viewSubmittedStory(Story story, Writer writer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
            Story  myStory = storyRepo.retrieveStory(story);
            
            if(myStory != null){
                return myStory;
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    

}