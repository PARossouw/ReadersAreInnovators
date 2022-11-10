/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interactions.Story_Transaction.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import java.sql.SQLException;


public class StoryTransactionRepoImpl extends JDBCConfig implements StoryTransactionRepo {

    @Override
    public boolean createEvent(Story story, Reader reader) throws SQLException {
    
        Boolean createdEvent = false;

            if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into story_transaction (story, reader) values (?, ?)");
            ps.setInt(1, story.getStoryID());
            ps.setInt(2, reader.getUserID());
           

            rowsAffected = ps.executeUpdate();

            createdEvent = true;
            }    
    
           closeConnection();
           return createdEvent ;
        
        
    }

    @Override
    public boolean updateEvent(Story story, String action, Reader reader) throws SQLException {
    
     Boolean updatedEvent = false;
            if (getConnection() != null) 
            {
            ps = getConnection().prepareStatement("update story_transaction set user = ? ,action = ? where story = ? ");
                  
            ps.setInt(1, reader.getUserID());
            ps.setString(2, action);
            ps.setInt(3, story.getStoryID());
            
            
            rs = ps.executeQuery();
            updatedEvent = true;
            
        }
        closeConnection();
        return updatedEvent;
        
    }
    
}
