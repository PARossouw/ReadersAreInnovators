package User_Interactions.Rating_Transaction.Model;

import Story.Model.Story;
import java.util.Calendar;
import user.Reader.Model.Reader;

/**
 *
 * @author piete
 */
public class RatingTransaction {
    
    private Integer ratingID;
    private Integer rating;
    private Calendar ratedOn;
    private Reader reader;
    private Story story;

    public RatingTransaction() {
    }

    public RatingTransaction(Integer ratingID, Integer rating, Calendar ratedOn, Reader reader, Story story) {
        this.ratingID = ratingID;
        this.rating = rating;
        this.ratedOn = ratedOn;
        this.reader = reader;
        this.story = story;
    }

    public Integer getRatingID() {
        return ratingID;
    }

    public void setRatingID(Integer ratingID) {
        this.ratingID = ratingID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Calendar getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Calendar ratedOn) {
        this.ratedOn = ratedOn;
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
        return "RatingTransaction{" + "ratingID=" + ratingID + ", rating=" + rating + ", ratedOn=" + ratedOn + ", reader=" + reader + ", story=" + story + '}';
    }
}
