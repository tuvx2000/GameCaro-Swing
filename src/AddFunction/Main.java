package AddFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static void main (String[] args){
/////test
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:DB.db");
        }catch (SQLException e){
            System.out.println("something went wrong!: " + e.getMessage());
        }

  /////  new AddFunction.Wellcome();

    }

}
