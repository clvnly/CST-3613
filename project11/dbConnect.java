
import java.sql.*;
public class dbConnect {
    public static Connection connection=null;
    dbConnect(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //System.out.println("Driver loaded");
            // Connect to a database
            connection = DriverManager.getConnection
                    ("jdbc:sqlserver://s16988308.onlinehome-server.com:1433;databaseName=CUNY_DB;integratedSecurity=false;" , "cst3613", "e369");
            //System.out.println("Database connected");
        }
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection connect(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //System.out.println("Driver loaded");
            // Connect to a database
            connection = DriverManager.getConnection
                    ("jdbc:sqlserver://s16988308.onlinehome-server.com:1433;databaseName=CUNY_DB;integratedSecurity=false;" , "cst3613", "e369");
            //System.out.println("Database connected");
            return connection;
        }
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
