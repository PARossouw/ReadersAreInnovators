package User_Interactions.Like_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiketransactionRepoImpl extends JDBCConfig implements LikeTransactionRepo {

    @Override
    public Boolean createLike(Reader reader, Story story) throws SQLException {

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into like_Transaction (reader, storyid) values (?, ?)");
            ps.setInt(1, reader.getUserID());
            ps.setInt(2, story.getStoryID());

            rowsAffected = ps.executeUpdate();

        }
        closeConnection();
        return rowsAffected == 1;

    }

    @Override//double check if this is an update or not - I think this needs to be an insert because we need to track the likes of a specific month
    public Boolean updateLike(Reader reader, Story story) throws SQLException {

        if (getConnection() != null) {

            //ps = getConnection().prepareStatement("update like_Transaction set isLiked = 0 where reader = ? and story = ?");
            ps = getConnection().prepareStatement("insert into like_Transaction (reader, story, isLiked) values (?, ?, IF( (select MAX(isLiked) from like_Transaction where reader = ? and story = ?) = 0, 1, 0))");
            ps.setInt(1, reader.getUserID());
            ps.setInt(2, story.getStoryID());
            ps.setInt(3, reader.getUserID());
            ps.setInt(4, story.getStoryID());

            rowsAffected = ps.executeUpdate();

        }
        closeConnection();
        return rowsAffected == 1;
    }

    @Override//supposed to get the total number of likes for each book - so you a map(key = story, value = amount of likes in that period)
    //getting top 20 ,most liked books of a certain period
    public Map<Story, Integer> getAllLikesInPeriod(Calendar month) throws SQLException {

        List<Story> storyList = new ArrayList<>();
        Map<Story, Integer> likeMap = new HashMap<>();

        if (getConnection() != null) {

            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from Story where storyID IN (select storyID from like_Transaction where month(likedOn) = ? and isLiked = 1)");

            ps.setDate(1, (java.sql.Date) month.getTime());
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

            for (int i = 0; i < storyList.size(); i++) {
                int count = 1;

                for (int j = i + 1; j < 10; j++) {
                    if (storyList.get(i).getStoryID() == storyList.get(j).getStoryID()) {
                        count++;
                        storyList.remove(j);
                        j--;

                    }
                }

                likeMap.put(storyList.get(i), count);
            }

        }
        closeConnection();

        return likeMap;

    }

}
