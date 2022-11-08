package Category.Dao;

import Category.Model.Category;
import JDBCConfig.JDBCConfig;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepoImpl extends JDBCConfig implements CategoryRepo {

    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    @Override
    public Boolean createCategory(Category category) throws SQLException {

        if (getConnection() != null) {//this null check okay?

            ps = getConnection().prepareStatement("insert into Category (Category) values(?)");
            ps.setString(1, category.getName());
            rowsAffected = ps.executeUpdate();

        }
        close();//use a finaly block for close();?

        return rowsAffected == 1;
    }

    @Override
    public Category retrieveCategory(int CategoryID) throws SQLException {

        Category category = new Category();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select Category, dateAdded from Category where categoryID = ?");
            ps.setInt(1, CategoryID);

            rs = ps.executeQuery();

            if (rs.next()) {
                category.setName(rs.getString("Category"));
                String x = rs.getString("dateAdded");                       //how to get date out
            }
        }
        close();
        return category;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        
        if(getConnection() != null){
            
            ps = getConnection().prepareStatement("update table category set Category = ? where categoryID = ?");
            ps.setString(1, category.getName());
            ps.setInt(2, category.getCategoryID());
            
            rowsAffected = ps.executeUpdate();
            
            
            
        }
        close();
        return rowsAffected == 1;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        
        List<Category> categoryList = new ArrayList<>();
        
        if(getConnection() != null){
            ps = getConnection().prepareStatement("select categoryid, category, dateAdded from category");
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                categoryList.add(new Category(rs.getInt("categoryid"), rs.getString("category"), rs.getString("dateAdded")));
                
            }
        }
        return categoryList;
    }
    
    private void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
    }


}
