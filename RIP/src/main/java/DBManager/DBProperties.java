package DBManager;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBProperties {

    private static Properties properties;
    
    static {
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
            properties.store(new FileOutputStream("src/main/resources/db.properties"), "Saving properties to file");
        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readProperty(String keyName) {
        return properties.getProperty(keyName, "There is no key in the properties file");
    }
}
