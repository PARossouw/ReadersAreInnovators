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
    public String likeStory(Reader reader, Story story) {

        if(reader != null && story != null)
        {
        
            try {
                if (likeTransactionRepo.getLike(reader, story)) {
                    likeTransactionRepo.updateLike(reader, story);
                    return  "The story like status has been successfully updated ";
                    
                } else {
                    likeTransactionRepo.createLike(reader, story);
                    return  "The story like status has been successfully updated ";
                    
                    
                    
                }   } catch (SQLException ex) {
                Logger.getLogger(Like_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "The story like status has not been updated";
       
    }

    @Override
    public Map<Story, Integer> getAllLikesInPeriod(Calendar startDate) {

        Boolean dateCheck = false;
        Map<Story, Integer> storyLikesMap = new HashMap<Story, Integer>();
        try {

            if (startDate == null) {
                dateCheck = false;
            }

            // Check to ensure that endDate is after the start date             
            startDate = Calendar.getInstance();
            int startMonth = startDate.get(Calendar.MONTH);
            int startYear = startDate.get(Calendar.YEAR);
            int startDay = startDate.get(Calendar.DATE);

            Calendar endDate = Calendar.getInstance();
            int endMonth = endDate.get(Calendar.MONTH);
            int endYear = endDate.get(Calendar.YEAR);
            int endDay = endDate.get(Calendar.DATE);

            if (endYear > startYear) {
                dateCheck = true;
            } else if (endYear == startYear && endMonth > startMonth) {
                dateCheck = true;
            } else if (endYear == startYear && endMonth == startMonth && endDay > startDay) {
                dateCheck = true;
            }

            if (dateCheck) {
                storyLikesMap = likeTransactionRepo.getAllLikesInPeriod(startDate, endDate);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Like_TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyLikesMap;
    }

}
