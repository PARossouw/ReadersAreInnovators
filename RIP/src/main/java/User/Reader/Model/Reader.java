package user.Reader.Model;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import java.util.Calendar;
import java.util.List;

public class Reader extends User {

    private List<Story> likedStories;
    private List<Category> preferredCategories;

    public Reader() {
    }

    public Reader(List<Story> likedStories, List<Category> preferredCategories, Integer UserID, String username, String email, String password, Boolean isActive, Calendar dateAdded) {
        super(UserID, username, email, password, isActive, dateAdded);
        this.likedStories = likedStories;
        this.preferredCategories = preferredCategories;
    }

    public Reader(List<Story> likedStories, List<Category> preferredCategories, Integer UserID, String username, String email, String password, Boolean isActive, Calendar dateAdded, String phoneNumber) {
        super(UserID, username, email, phoneNumber, password, isActive, dateAdded);
        this.likedStories = likedStories;
        this.preferredCategories = preferredCategories;
    }

    public List<Story> getLikedStories() {
        return likedStories;
    }

    public void setLikedStories(List<Story> likedStories) {
        this.likedStories = likedStories;
    }

    public List<Category> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(List<Category> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    @Override
    public String toString() {
        return "Reader{" + "likedStories=" + likedStories + ", preferredCategories=" + preferredCategories + '}';
    }

}
