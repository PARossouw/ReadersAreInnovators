/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Service.Like_TransactionService;
import java.util.Calendar;
import java.util.Map;

public class LikeTransactionControllerImpl implements LikeTransactionController {

    private Like_TransactionService likeTransactionService;

    @Override
    public Integer likeStory(Reader reader, Story story) {
        return likeTransactionService.likeStory(reader, story);
    }

    @Override
    public String changeLike(Reader reader, Story story) {
        return likeTransactionService.changeLike(reader, story);
    }

    @Override
    public Map<Story, Integer> getAllLikesInPeriod(Calendar startDate, Calendar endDate) {
        return likeTransactionService.getAllLikesInPeriod(startDate, endDate);
    }

}
