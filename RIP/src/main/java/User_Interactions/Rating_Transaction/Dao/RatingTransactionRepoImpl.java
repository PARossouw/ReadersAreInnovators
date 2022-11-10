package User_Interactions.Rating_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RatingTransactionRepoImpl extends JDBCConfig implements RatingTransactionRepo {

    @Override
    public Boolean createRating(Story story, Reader reader, int rating) throws SQLException {

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into rating_Transaction (rating, reader, storyID) values (?, ?, ?)");
            ps.setInt(1, rating);
            ps.setInt(2, reader.getUserID());
            ps.setInt(3, story.getStoryID());

            rowsAffected = ps.executeUpdate();

        }
        closeConnection();
        return rowsAffected == 1;
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
        closeConnection();
        return rowsAffected == 1;

    }

    
}
