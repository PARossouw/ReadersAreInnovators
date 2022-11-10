package User_Interactions.Comment.Model;

import Story.Model.Story;
import java.util.Calendar;
import User.Model.Reader;

public class Comment {
    
    private Integer commentID;
    private String commentBody;
    private Calendar commentedOn;
    private Reader reader;
    private Story story;

    public Comment() {
    }

    public Comment(Integer commentID, String commentBody, Calendar commentedOn, Reader reader, Story story) {
        this.commentID = commentID;
        this.commentBody = commentBody;
        this.commentedOn = commentedOn;
        this.reader = reader;
        this.story = story;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Calendar getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Calendar commentedOn) {
        this.commentedOn = commentedOn;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentID=" + commentID + ", commentBody=" + commentBody + ", commentedOn=" + commentedOn + ", reader=" + reader + ", story=" + story + '}';
    }
}
