package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Service.Like_TransactionService;
import java.util.Calendar;
import java.util.Map;

public class LikeTransactionControllerImpl implements LikeTransactionController {

    private Like_TransactionService likeTransactionService;

    @Override
    public String likeStory(Reader reader, Story story) {
        return likeTransactionService.likeStory(reader, story);
    }

    @Override
    public String changeLike(Reader reader, Story story) {
        return likeTransactionService.likeStory(reader, story);
    }

    @Override
    public Map<Story, Integer> getAllLikesInPeriod(Calendar month) {
        return likeTransactionService.getAllLikesInPeriod(month);
    }

}
