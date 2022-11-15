package User_Interactions.Rating_Transaction.Service;

import Story.Dao.StoryRepo;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Dao.RatingTransactionRepo;
import java.sql.SQLException;
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
    public Double rateStory(Story story, Reader reader, Integer rating) {
        
        try {
            if (ratingRepo.getRating(story, reader) == null) {
                ratingRepo.createRating(story, reader, rating);
            } else {
                ratingRepo.updateRating(story, reader, rating);
            }
            return storyRepo.retrieveStory(story).getAvgRating();
        } catch (SQLException ex) {
            Logger.getLogger(RatingTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}