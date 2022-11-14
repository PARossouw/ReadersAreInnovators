
package User_Interactions.Like_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Dao.LikeTransactionRepo;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Like_TransactionServiceImpl implements Like_TransactionService {

    private LikeTransactionRepo likeTransactionRepo;
    
    @Override
    public Integer likeStory(Reader reader,Story story) {
        
        Boolean successfullyLikedStory = false;
        Integer totalNumberOfLikes = 0;
         Boolean allowedToLike = false; 
        
        try {
            if(reader != null && story != null)
            {
                allowedToLike = true;
            }
            
           if(allowedToLike)
            {
           successfullyLikedStory =  likeTransactionRepo.createLike(reader, story);
            }
           
           if(successfullyLikedStory)
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
    
        Boolean allowedToChangeLike = false; 
        Boolean successfullyLikedStory = false;
        String successMessage = "";
        
        try {
            
            if(reader != null && story != null)
            {
                allowedToChangeLike = true;
            }
            
            
            if(allowedToChangeLike)
            {
           successfullyLikedStory =  likeTransactionRepo.createLike(reader, story);
            }
           
           if(successfullyLikedStory)
           {
              successMessage = "The like count has been updated successfully.";
           }
           else 
           {
               successMessage = "Unfortunetely, The like count has not been updated.";
           }
            }
        catch (SQLException ex) 
        {
            Logger.getLogger(Like_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return successMessage;
    }

    @Override
    public Map<Story, Integer> getAllLikesInPeriod(Calendar startDate) {
       
        Boolean dateCheck = false;
         Map<Story, Integer> storyLikesMap = new HashMap<Story, Integer>();
        try {

            // Check to ensure that endDate is after the start date             
             startDate  = Calendar.getInstance();
             int startMonth = startDate.get(Calendar.MONTH);
             int startYear = startDate.get(Calendar.YEAR);
             int startDay = startDate.get(Calendar.DATE);
             
             Calendar endDate  = Calendar.getInstance();
             int endMonth = endDate.get(Calendar.MONTH);
             int endYear = endDate.get(Calendar.YEAR);
             int endDay = endDate.get(Calendar.DATE);
             
             if(endYear > startYear)
             {
                 dateCheck = true;
             }
             else if(endYear == startYear && endMonth>startMonth)
             {
                 dateCheck = true;
             }
             else if(endYear == startYear && endMonth == startMonth && endDay>startDay)
             {
                 dateCheck = true;
             }
            
             if(dateCheck)
             {
            storyLikesMap = likeTransactionRepo.getAllLikesInPeriod(startDate, endDate);
             }
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(Like_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return storyLikesMap;
    }
    
}
