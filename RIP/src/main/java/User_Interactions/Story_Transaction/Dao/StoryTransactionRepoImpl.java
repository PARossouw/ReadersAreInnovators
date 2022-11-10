/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interactions.Story_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import User.Model.User;
import java.sql.SQLException;


//need to integrate the String action as an argument into this class
public class StoryTransactionRepoImpl extends JDBCConfig implements StoryTransactionRepo {

    @Override
    public boolean createEvent(Story story, User user, String action) throws SQLException {
    
        Boolean createdEvent = false;

            if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into story_transaction (story, user, action) values (?, ?, ?)");
            ps.setInt(1, story.getStoryID());
             ps.setInt(2, user.getUserID());
            ps.setString(3, action);
           

            rowsAffected = ps.executeUpdate();

            createdEvent = true;
            }    
    
           closeConnection();
           return createdEvent ;
        
        
    }


    
}
