/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package User.Model;

import User_Interactions.Story_Transaction.Model.StoryTransaction;
import java.util.Calendar;
import java.util.List;


public class AdminEditor extends Editor {

     public AdminEditor() {
    }

    public AdminEditor(List<StoryTransaction> storyTransactions, Integer UserID, String username, String email, String password, Boolean isActive, Calendar dateAdded) {
        super(storyTransactions, UserID, username, email, password, isActive, dateAdded);
    }
    
}
