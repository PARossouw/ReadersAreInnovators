package User_Interactions.Story_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//need to integrate the String action as an argument into this class
public class StoryTransactionRepoImpl implements StoryTransactionRepo {
    
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public boolean createEvent(Story story, User user, String action) throws SQLException {
        
        con = DBManager.getConnection();
        //return true;

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into story_transaction (story, user, action) values (?, ?, ?)");
                
                ps.setInt(1, story.getStoryID());
                ps.setInt(2, user.getUserID());
                ps.setString(3, action);

                rowsAffected = ps.executeUpdate();

                //createdEvent = true;
                setIsApprovedStatus(story);
                return rowsAffected == 1;
            }
        } finally {

            close();
        }
        return false;

    }
    
    private boolean setIsApprovedStatus(Story story) throws SQLException{
        
        con = DBManager.getConnection();
        try {
            if (con != null) {
                
                if(!story.getIsApproved()){
                ps = con.prepareStatement("update story set isapproved = ?, isdraft = ? where storyid = ?");
                    
                ps.setBoolean(1, story.getIsApproved());
                ps.setBoolean(2, true);
                ps.setInt(3, story.getStoryID());
                }else{
                    
                ps = con.prepareStatement("update story set isapproved = ? where storyid = ?");
                
                ps.setBoolean(1, story.getIsApproved());
                ps.setInt(2, story.getStoryID());
                }
                

                rowsAffected = ps.executeUpdate();

                //createdEvent = true;
                return rowsAffected == 1;
            }
        } finally {

            close();
        }

        return false;
    }
    
    public void close() throws SQLException {

        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (con != null) {
            con.close();
        }
    }

}
