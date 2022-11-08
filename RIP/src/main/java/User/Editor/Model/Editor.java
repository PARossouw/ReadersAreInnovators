package user.Editor.Model;

import User.Model.User;
import User_Interactions.StoryTransaction.Model.StoryTransaction;
import java.util.Calendar;
import java.util.List;

public class Editor extends User {

    private List<StoryTransaction> storyTransactions;

    public Editor() {
    }

    public Editor(List<StoryTransaction> storyTransactions) {
        this.storyTransactions = storyTransactions;
    }

    public Editor(List<StoryTransaction> storyTransactions, Integer UserID, String username, String email, String password, Boolean isActive, Calendar dateAdded) {
        super(UserID, username, email, password, isActive, dateAdded);
        this.storyTransactions = storyTransactions;
    }

    public List<StoryTransaction> getStoryTransactions() {
        return storyTransactions;
    }

    public void setStoryTransactions(List<StoryTransaction> storyTransactions) {
        this.storyTransactions = storyTransactions;
    }

    @Override
    public String toString() {
        return "Editor{" + "storyTransactions=" + storyTransactions + '}';
    }

}
