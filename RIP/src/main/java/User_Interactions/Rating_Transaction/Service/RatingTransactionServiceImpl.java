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
    public List<String> rateStory(String ratingInfo) {

        List<String> response = new ArrayList<>();
        String[] rInfo = ratingInfo.split(":");
        try {
            //   if (ratingRepo.getRating(story, reader) == null) {
            Story story = new Story();
            story.setStoryID(Integer.parseInt(rInfo[0]));

            Reader reader = new Reader();
            reader.setUserID(Integer.parseInt(rInfo[1]));

            ratingRepo.createRating(story, reader, Integer.parseInt(rInfo[2]));
            response.add("Story has been rated " + Integer.parseInt(rInfo[2]) + " stars!");

            return response;
        } catch (SQLException ex) {
            Logger.getLogger(RatingTransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

            response.add("Story has not been rated");
            return response;
        }
    }
}
