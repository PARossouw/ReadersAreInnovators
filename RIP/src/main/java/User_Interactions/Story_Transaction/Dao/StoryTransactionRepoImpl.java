package User_Interactions.Story_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;

//need to integrate the String action as an argument into this class
public class StoryTransactionRepoImpl extends DBManager implements StoryTransactionRepo {

    @Override
    public boolean createEvent(Story story, User user, String action) throws SQLException {
        //user.getUserID();
        //story.getStoryID();
        return true;

//        try {
//            if (getConnection() != null) {
//
//                ps = getConnection().prepareStatement("insert into story_transaction (story, user, action) values (?, ?, ?)");
//                ps.setInt(1, 1);
//                ps.setInt(2, 1);
//                ps.setString(3, action);
//
//                rowsAffected = ps.executeUpdate();
//
//                //createdEvent = true;
//                return rowsAffected == 1;
//            }
//        } finally {
//
//            close();
//        }
//
//        return false;

    }

}
