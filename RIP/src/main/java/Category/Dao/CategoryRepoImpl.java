package Category.Dao;

import Category.Model.Category;
import DBManager.DBManager;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import User.Model.Reader;
import User.Model.User;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class CategoryRepoImpl implements CategoryRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public Boolean createCategory(Category category) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into Category (Category) values(?)");
                ps.setString(1, category.getName());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Category retrieveCategory(Category category) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("select Category, dateAdded from Category where categoryID = ?");
                ps.setInt(1, category.getCategoryID());
                rs = ps.executeQuery();

                if (rs.next()) {
                    category.setName(rs.getString("Category"));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateAdded"));
                    category.setDateAdded(calendar);

                }
            }
        } finally {
            close();
        }
        return category;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("update table category set Category = ? where categoryID = ?");
                ps.setString(1, category.getName());
                ps.setInt(2, category.getCategoryID());

                rowsAffected = ps.executeUpdate();

            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {

        con = DBManager.getConnection();

        List<Category> categoryList = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select categoryid, category, dateAdded from category");
                rs = ps.executeQuery();

                while (rs.next()) {

                    // Calendar calendar = Calendar.getInstance();
                    // calendar.setTime(rs.getDate("dateAdded"));
                    Category category = new Category();
                    category.setName(rs.getString("category"));
                    category.setCategoryID(rs.getInt("categoryid"));
                    categoryList.add(category);

                    // categoryList.add(new Category(rs.getInt("categoryid"), rs.getString("category"), calendar));
                }

            }
            //-----Testing code------
//         List<Category> categoryListTest = new ArrayList<>();
//            Category cat1 = new Category();
//            Category cat2 = new Category();
//            Category cat3 = new Category();
//            
//            cat1.setName("Thriller");
//            cat2.setName("Science");
//            cat3.setName("Religious");
//            
//            
//            
//            
//            
//            
//            categoryListTest.add(cat1);
//            categoryListTest.add(cat2);
//            categoryListTest.add(cat3);
            // --------------------
        } finally {
            close();
        }
        return categoryList;
    }

    @Override
    public List<Category> getPreferredCategories(User reader) throws SQLException {

        con = DBManager.getConnection();

        List<Category> categoryList = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select categoryID, category from Category "
                        + "where CategoryID IN (select category from user_category where user = ?)");
                ps.setInt(1, reader.getUserID());
                rs = ps.executeQuery();

                while (rs.next()) {
                    categoryList.add(new Category(rs.getInt("categoryID"), rs.getString("category"), null));
                }
            }
        } finally {
            close();
        }
        return categoryList;
    }

    @Override
    public Boolean addPreferredCategories(Reader reader, List<Category> categories) throws SQLException {

       con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("insert into user_category (user, category) values(?, ?)");

                    ps.setInt(1, reader.getRoleID());
                    ps.setInt(2, categories.get(0).getCategoryID());
                    rowsAffected = ps.executeUpdate();

            }
        } finally {
            close();
        }
        return rowsAffected == categories.size();
    }
    
    
      @Override
    public Boolean addPreferredCategoriesToUser(Reader reader) throws SQLException {

       con = DBManager.getConnection();

        try {
            if (con != null) {
                
                for(int i =  0 ; i<reader.getPreferredCategories().size(); i++)
                {
                ps = con.prepareStatement("insert into user_category (user, category) values(?, ?)");

                    ps.setInt(1, reader.getUserID());
                    ps.setInt(2, reader.getPreferredCategories().get(i).getCategoryID());
                    rowsAffected = ps.executeUpdate();
                }
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }
    
    

    
    
    

    @Override
    public List<Category> getStoryCategories(Story story) throws SQLException {

        con = DBManager.getConnection();

        List<Category> categories = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select categoryid, category, dateAdded from Category where categoryid IN (select category from story_category where story = ?)");
                ps.setInt(1, story.getViews());
                rs = ps.executeQuery();

                while (rs.next()) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateAdded"));

                    categories.add(new Category(rs.getInt("categoryID"), rs.getString("category"), calendar));
                }
            }
        } finally {
            close();
        }
        return categories;
    }

    @Override
    public HashMap<String, Integer> topCategoriesForMonth(String month) throws SQLException { //DISCUSS WITH GROUP
        
        con = DBManager.getConnection();

        HashMap<String, Integer> topCategories = new HashMap<String, Integer>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select categoryID, c.category, dateAdded, count(vt.story) as categoryViews from category c "
                        + "inner join story_category sc on c.categoryID = sc.category "
                        + "inner join story s on sc.story = s.storyID "
                        + "inner join view_transaction vt on s.storyID = vt.story "
                        + "where month(dateViewed) = month(current_timestamp) and year(dateViewed) = year(current_timestamp) "
                        + "group by c.category order by categoryViews desc limit 3");
                rs = ps.executeQuery();

                while (rs.next()) {
                    String category = rs.getString("category");
                    int views = rs.getInt("categoryViews");
                    
                    
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("dateAdded"));

                    topCategories.put(category, views);
                    if (topCategories.size() == 3){
                        break;
                    }
                }
            }
        } finally {
            close();
        }
        return topCategories;

    }

    @Override
    public Boolean addCategoriesToStory(Story story, List<Category> categoryList) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                
                //for(int i =  0 ; i<categories.size(); i++)
                //{
                ps = con.prepareStatement("insert into story_category (story, category) values (?,?)");

                    ps.setInt(1, story.getStoryID());
                    ps.setInt(2, categoryList.get(0).getCategoryID());
                    rowsAffected = ps.executeUpdate();
                //}
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    public void close() throws SQLException {

        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
