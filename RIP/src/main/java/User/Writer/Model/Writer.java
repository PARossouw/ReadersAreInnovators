package user.Writer.Model;

import Category.Model.Category;
import Story.Model.Story;
import java.util.Calendar;
import java.util.List;
import user.Reader.Model.Reader;

public class Writer extends Reader{

    private List<Story> allWriterStories;

    public Writer() {
    }

    public Writer(List<Story> allWriterStories) {
        this.allWriterStories = allWriterStories;
    }

    public Writer(List<Story> allWriterStories, List<Story> likedStories, List<Category> preferredCategories, Integer UserID, String username, String email, String password, Boolean isActive, Calendar dateAdded, String phoneNumber) {
        super(likedStories, preferredCategories, UserID, username, email, password, isActive, dateAdded, phoneNumber);
        this.allWriterStories = allWriterStories;
    }

    public List<Story> getAllWriterStories() {
        return allWriterStories;
    }

    public void setAllWriterStories(List<Story> allWriterStories) {
        this.allWriterStories = allWriterStories;
    }

    @Override
    public String toString() {
        return "Writer{" + "allWriterStories=" + allWriterStories + '}';
    }

}
