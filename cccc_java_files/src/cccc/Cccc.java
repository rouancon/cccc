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
    private final String password = "DBSpring2018";
    private final String serverName = "localhost";
    private final String connection = "jdbc:mysql://localhost:3306/";
    private final String encoding = "?characterEncoding=UTF-8&useSSL=false";
    private final String dbName = "arnoldrouan";
    
    public Connection getConnection() throws SQLException 
    {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        conn = DriverManager.getConnection(connection+dbName+encoding,connectionProps);
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
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        } 
        catch (SQLException e) 
        {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }
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