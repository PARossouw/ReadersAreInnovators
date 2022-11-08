package Category.Model;

import java.util.Date;


public class Category {
    
    private Integer CategoryID;
    private String Name;
    private Date dateAdded;
    
    public Category() {
    }

    public Category(Integer CategoryID, String Name, Date dateAdded) {
        this.CategoryID = CategoryID;
        this.Name = Name;
        this.dateAdded = dateAdded;
    }

    public Integer getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Integer CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    @Override
    public String toString() {
        return "Category{" + "CategoryID=" + CategoryID + ", Name=" + Name + ", dateAdded=" + dateAdded + '}';
    }
    
    

    
    
    
    
    
    
    
    
    

}