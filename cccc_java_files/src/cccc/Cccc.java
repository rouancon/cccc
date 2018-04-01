/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
db.mysql.url="jdbc:mysql://localhost:3306/db?characterEncoding=UTF-8&useSSL=false"
*/
package cccc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Cccc {

    private final String userName = "root";
    private final String password = "kylund";
    private final String serverName = "localhost";
    private final int portNumber = 3306;
    private final String dbName = "arnoldrouan";
    private final boolean useSSL = false;
    
    public Connection getConnection() throws SQLException 
    {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);
        conn = DriverManager.getConnection("jdbc:mysql://"
                        + this.serverName + ":" + this.portNumber + "/" + this.dbName + "?characterEncoding=UTF-8&useSSL=false",
                        connectionProps);
        return conn;
    }
    
    public void run() 
    {
        Connection conn = null;
        try 
        {
            conn = this.getConnection();
            System.out.println("Connected to database");
            LoginPage login = new LoginPage(conn);
            login.setVisible(true);
        } 
        catch (SQLException e) 
        {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }
    }
    
    public static void main(String[] args) 
    {
        Cccc app = new Cccc();
        app.run();
    }
}

// glaban0
// JNzTg2Pu

// sgerdes3
// IVWEdCSiW