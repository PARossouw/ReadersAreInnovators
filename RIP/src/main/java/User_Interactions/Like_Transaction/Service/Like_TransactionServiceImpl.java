/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interactions.Like_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Dao.LikeTransactionRepo;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Like_TransactionServiceImpl implements Like_TransactionService {

    private LikeTransactionRepo likeTransactionRepo;
    
    @Override
    public Integer likeStory(Reader reader,Story story) {
        
        Boolean successfullyLikedStory = false;
        Integer totalNumberOfLikes = 0;
        
        try {
           successfullyLikedStory =  likeTransactionRepo.createLike(reader, story);
           
           if(successfullyLikedStory == true)
           {
               totalNumberOfLikes = story.getLikes()+1;
           }
            }
        catch (SQLException ex) 
        {
            Logger.getLogger(Like_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return totalNumberOfLikes;
    }

    @Override
    public String changeLike(Reader reader, Story story) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<Story, Integer> getAllLikesInPeriod(Calendar startDate, Calendar endDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
