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
        String username = "root";
        String Password = "kylund";
        String connection = "jdbc:mysql://localhost:3306/";
        String database = "arnoldrouan";
        String encoding = "?characterEncoding=UTF-8&useSSL=false";
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        conn = DriverManager.getConnection(connection+database+encoding,
                        connectionProps);
        return conn;
    }
    
    public void run() 
    {
        DatabaseConnect db = new DatabaseConnect();
        db.setVisible(true);

        // If login doesn't work try using the commented code below instead of the 2 lines above
//        Connection conn = null;
//        try 
//        {
//            conn = this.getConnection();
//            System.out.println("Connected to database");
//            LoginPage lp = new LoginPage(conn);
//            lp.setVisible(true);
//        } 
//        catch (SQLException e) 
//        {
//            System.out.println("ERROR: Could not connect to the database");
//            e.printStackTrace();
//            return;
//        }
    }
    
    public static void main(String[] args) 
    {
        Cccc app = new Cccc();
        app.run();
    }
}

// customer
// glaban0
// JNzTg2Pu

// employee
// mjellis1
// a6HLXInyZN
// Or jelly