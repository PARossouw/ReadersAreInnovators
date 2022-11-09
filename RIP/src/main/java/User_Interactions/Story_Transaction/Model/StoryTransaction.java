package User_Interactions.Story_Transaction.Model;

import Story.Model.Story;
import User.Model.User;
import java.util.Calendar;


public class StoryTransaction {
    
    private Integer eventID;
    private Story story;
    private User user;
    private String action;
    private Calendar actionPerformedOn;

    public StoryTransaction() {
    }

    public StoryTransaction(Integer eventID, Story story, User user, String action, Calendar actionPerformedOn) {
        this.eventID = eventID;
        this.story = story;
        this.user = user;
        this.action = action;
        this.actionPerformedOn = actionPerformedOn;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Calendar getActionPerformedOn() {
        return actionPerformedOn;
    }

    public void setActionPerformedOn(Calendar actionPerformedOn) {
        this.actionPerformedOn = actionPerformedOn;
    }

    @Override
    public String toString() {
        return "StoryTransaction{" + "eventID=" + eventID + ", story=" + story + ", user=" + user + ", action=" + action + ", actionPerformedOn=" + actionPerformedOn + '}';
    }
    
    
    

}