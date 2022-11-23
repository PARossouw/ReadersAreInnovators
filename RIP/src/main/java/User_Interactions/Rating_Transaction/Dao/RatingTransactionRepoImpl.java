package User_Interactions.Rating_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Model.RatingTransaction;
import java.sql.SQLException;
import java.util.Calendar;

public class RatingTransactionRepoImpl extends DBManager implements RatingTransactionRepo {

    @Override
    public Boolean createRating(Story story, Reader reader, int rating) throws SQLException {

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into rating_Transaction (rating, reader, storyID) values (?, ?, ?)");
            ps.setInt(1, rating);
            ps.setInt(2, reader.getUserID());
            ps.setInt(3, story.getStoryID());

            rowsAffected = ps.executeUpdate();

        }
        close();
        return rowsAffected == 1;
    }
    
    @Override
    public RatingTransaction getRating(Story story, Reader reader) throws SQLException {
        
        RatingTransaction rating = new RatingTransaction();
        
        if (getConnection()!=null) {
            ps = getConnection().prepareStatement("select ratingID, rating, ratedOn, reader, story from rating_transaction where story = ? and reader = ?");
            ps.setInt(1, story.getStoryID());
            ps.setInt(2, reader.getUserID());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                rating.setRatingID(rs.getInt("ratingID"));
                rating.setRating(rs.getInt("rating"));
                
                java.util.Date createdOn = rs.getDate("ratedOn");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);
                
                rating.setReader(reader);
                rating.setStory(story);
            }
        }
        close();
        return rating;
    }

    @Override
    public Boolean updateRating(Story story, Reader reader, int rating) throws SQLException {

        if (getConnection() != null) {

            //need to have triggers ofr this to update the avgrating on story
            ps = getConnection().prepareStatement("update rating_Transaction set rating = ? where storyID = ? and reader = ?");
            ps.setInt(1, rating);
            ps.setInt(2, story.getStoryID());
            ps.setInt(3, reader.getUserID());

            rowsAffected = ps.executeUpdate();

        }
        close();
        return rowsAffected == 1;
    }
}