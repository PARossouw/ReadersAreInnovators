package User_Interactions.Like_Transaction.Model;

import Story.Model.Story;
import java.util.Calendar;
import user.Reader.Model.Reader;

/**
 *
 * @author piete
 */
public class LikeTransaction {

    private Integer likeID;
    private Calendar likedOn;
    private Reader reader;
    private Story story;
    private Boolean isLiked;

    public LikeTransaction() {
    }

    public LikeTransaction(Integer likeID, Calendar likedOn, Reader reader, Story story, Boolean isLiked) {
        this.likeID = likeID;
        this.likedOn = likedOn;
        this.reader = reader;
        this.story = story;
        this.isLiked = isLiked;
    }

    public Integer getLikeID() {
        return likeID;
    }

    public void setLikeID(Integer likeID) {
        this.likeID = likeID;
    }

    public Calendar getLikedOn() {
        return likedOn;
    }

    public void setLikedOn(Calendar likedOn) {
        this.likedOn = likedOn;
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

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return "LikeTransaction{" + "likeID=" + likeID + ", likedOn=" + likedOn + ", reader=" + reader + ", story=" + story + ", isLiked=" + isLiked + '}';
    }
}
