package User_Interactions.Story_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;

//need to integrate the String action as an argument into this class
public class StoryTransactionRepoImpl extends DBManager implements StoryTransactionRepo {

    @Override
    public boolean createEvent(Story story, User user, String action) throws SQLException {
        //return true;

//        //Boolean createdEvent = false;
        if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into story_transaction (story, user, action) values (?, ?, ?)");
            ps.setInt(1, story.getStoryID());
            ps.setInt(2, user.getUserID());
            ps.setString(3, action);

            rowsAffected = ps.executeUpdate();

            //createdEvent = true;
        }

        close();
        return rowsAffected == 1;

    }

}
