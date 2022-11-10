package User_Interactions.Like_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LiketransactionRepoImpl extends JDBCConfig implements LikeTransactionRepo {

    @Override
    public boolean createLike(Reader reader, Story story) throws SQLException {

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into like_Transaction (reader, storyid) values (?, ?)");
            ps.setInt(1, reader.getUserID());
            ps.setInt(2, story.getStoryID());

            rowsAffected = ps.executeUpdate();

        }
        closeConnection();
        return rowsAffected == 1;

    }

    @Override
    public boolean updateLike(Reader reader, Story story) throws SQLException {

        if (getConnection() != null) {

            //what is this supposed to do? if it's supposed to 'unlike' a story then we need an isActive on like_Transaction or a boolean isLiked
            ps = getConnection().prepareStatement("update like_Transaction set like");

        }
    }

    @Override//also not sure of what this is supposed to do
    public List<Story> getAllLikesInPeriod(Reader reader) throws SQLException {//I'm making this return a list of story.. it was originally returning a list of reader

        List<Story> storyList = new ArrayList<>();

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from Story where storyID = (select storyID from like_Transaction where reader = ?) and ");

            ps.setInt(1, reader.getUserID());
            rs = ps.executeQuery();

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
        return storyList;

    }

}
