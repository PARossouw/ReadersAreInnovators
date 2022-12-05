package User_Interactions.Rating_Transaction.Service;

import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Dao.RatingTransactionRepo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatingTransactionServiceImpl implements RatingTransactionService {

    private final RatingTransactionRepo ratingRepo;
    private final StoryRepo storyRepo;

    public RatingTransactionServiceImpl(RatingTransactionRepo ratingRepo, StoryRepo storyRepo) {
        this.ratingRepo = ratingRepo;
        this.storyRepo = storyRepo;
    }

    @Override
    public String rateStory(String ratingInfo) {
        //ratingInfo.replace("\"", "");
        String response;

        ratingInfo = ratingInfo.substring(1, ratingInfo.length() - 1);

        String[] rInfo = ratingInfo.split(":");
        try {
            Story story = new Story();

            story.setStoryID(Integer.parseInt(rInfo[0]));

            Reader reader = new Reader();
            reader.setUserID(Integer.parseInt(rInfo[1]));
            if (!ratingRepo.getRating(story, reader)) {
                if (ratingRepo.createRating(story, reader, Integer.parseInt(rInfo[2]))) {

                    response = "Story has been rated " + Integer.parseInt(rInfo[2]) + " stars!";
                    return response;
                }
            }else{
                response = "You have already rated this story";
                    return response;
            }

        } catch (SQLException ex) {
            Logger.getLogger(RatingTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "Unfortunately story has not been rated";
    }
}
