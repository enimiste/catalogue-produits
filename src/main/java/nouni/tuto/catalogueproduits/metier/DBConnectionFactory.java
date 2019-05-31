/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import nouni.tuto.catalogueproduits.config.Config;

/**
 *
 * @author HP
 */
public class DBConnectionFactory {
    private static Connection instance;
    
    static {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            instance= DriverManager.getConnection(Config.DERBY_DB_URL);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private DBConnectionFactory() {
        
    }
    
    public static Connection getInstance() {
        return instance;
    }
}
