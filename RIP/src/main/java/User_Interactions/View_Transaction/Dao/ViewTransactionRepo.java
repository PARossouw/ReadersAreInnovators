package User_Interactions.View_Transaction.Dao;

import User_Interactions.View_Transaction.Model.ViewTransaction;
import java.sql.SQLException;

public interface ViewTransactionRepo {
    
    boolean createView(ViewTransaction view) throws SQLException;
    
}