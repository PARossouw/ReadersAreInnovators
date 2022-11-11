/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interactions.View_Transaction.Controller;

import Story.Model.Story;
import User.Model.User;
import User_Interactions.View_Transaction.Service.ViewTransactionService;


public class ViewTransactionControllerImpl implements ViewTransactionController {

    private ViewTransactionService view_transaction_service ;
    
    @Override
    public String viewStory(Story story, User user) { 
    
        return view_transaction_service.viewStory(story, user);
    }
    
}
