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
    public boolean createRating(Story story, Reader reader, int rating) throws SQLException {

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
    public boolean updateRating(Story story, Reader reader, int rating) throws SQLException {

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

    @Override//the five highest? the top 10%? ..I'll return a list in ascending order for now
    public List<Story> getHighestRatedStoriesForMonth() throws SQLException {//I cahnge the argument here from calendar to nothing - make sure you can just minus a month here tho

        List<Story> storyList = new ArrayList<>();

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from Story where storyID IN (select storyID from rating_Transaction where ratedOn DATE_SUB( (select CURRENT_TIMESTAMP), INTERVAL 1 MONTH ))");

            while (rs.next()) {

                int storyID = rs.getInt("storyID");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String description = rs.getString("description");
                String imagePath = rs.getString("imagePath");
                String body = rs.getString("body");
                boolean isDraft = rs.getBoolean("isDraft");
                boolean isActive = rs.getBoolean("isActive");

                Date createdOn = rs.getDate("createdOn");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);

                boolean allowComments = rs.getBoolean("allowComments");
                boolean isApproved = rs.getBoolean("isApproved");
                int views = rs.getInt("views");
                int likes = rs.getInt("likes");
                double avgRating = rs.getDouble("avgRating");

                Story story = new Story(storyID, title, writer, description,
                        imagePath, body, isDraft, isActive,
                        calendar, allowComments, isApproved,
                        views, likes, avgRating);

                storyList.add(story);
            }

        }
        for (int i = 0; i < storyList.size(); i++) {

            if (i == storyList.size() - 2) {
                break;
            }

            if (storyList.get(i).getAvgRating() < storyList.get(i + 1).getAvgRating()) {

                Collections.swap(storyList, i, i + 1);
                i = 0;

            }

        }
        return storyList;
    }

}
