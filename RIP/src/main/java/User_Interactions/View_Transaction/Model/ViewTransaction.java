package User_Interactions.View_Transaction.Model;

import Story.Model.Story;
import java.util.Calendar;
import user.Reader.Model.Reader;

/**
 *
 * @author piete
 */
public class ViewTransaction {

    private Integer viewID;
    private Calendar dateViewed;
    private Reader reader;
    private Story story;

    public ViewTransaction() {
    }

    public ViewTransaction(Integer viewID, Calendar dateViewed, Reader reader, Story story) {
        this.viewID = viewID;
        this.dateViewed = dateViewed;
        this.reader = reader;
        this.story = story;
    }

    public Integer getViewID() {
        return viewID;
    }

    public void setViewID(Integer viewID) {
        this.viewID = viewID;
    }

    public Calendar getDateViewed() {
        return dateViewed;
    }

    public void setDateViewed(Calendar dateViewed) {
        this.dateViewed = dateViewed;
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
        return "ViewTransaction{" + "viewID=" + viewID + ", dateViewed=" + dateViewed + ", reader=" + reader + ", story=" + story + '}';
    }
}
