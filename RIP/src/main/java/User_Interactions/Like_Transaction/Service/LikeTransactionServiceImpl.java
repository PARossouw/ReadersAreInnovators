package User_Interactions.Like_Transaction.Service;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Dao.LikeTransactionRepo;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LikeTransactionServiceImpl implements LikeTransactionService {
    

    private final LikeTransactionRepo likeTransactionRepo;

    public LikeTransactionServiceImpl(LikeTransactionRepo likeTransactionRepo) {
        this.likeTransactionRepo = likeTransactionRepo;
    }

    @Override
    public String likeStory(Reader reader, Story story) {

        if(reader != null && story != null)
        {
            try {
                if (likeTransactionRepo.getLike(reader, story)) {
//                    likeTransactionRepo.updateLike(reader, story);
                    return  "The story like status has been successfully updated ";
                    
                } else {
                    likeTransactionRepo.createLike(reader, story);
                    return  "The story like status has been successfully updated ";
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(LikeTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "The story like status has not been updated";
    }

    @Override
    public Map<String, Integer> getAllLikesInPeriod(String month) {

        Boolean dateCheck = false;
        Map<String, Integer> storyLikesMap = new HashMap<String, Integer>();
        try {

            if (month == null) {
                dateCheck = false;
            }

            if (new SimpleDateFormat("yyyy-mm").parse(month).before(Date.valueOf(LocalDate.now()))){
                dateCheck = true;
            }
            if (!new SimpleDateFormat("yyyy-mm").parse(month).equals(Date.valueOf(LocalDate.now()))){
                dateCheck = true;
            }
            if (dateCheck) {
                storyLikesMap = likeTransactionRepo.getAllLikesInPeriod(month);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LikeTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LikeTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storyLikesMap;
    }
}
