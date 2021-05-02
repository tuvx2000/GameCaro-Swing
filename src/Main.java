import caro.GameState;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.MainView;
import org.sqlite.SQLiteJDBCLoader;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;


public class Main {
    public static void main (String[] args) throws SQLServerException {

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("123456");
        ds.setServerName("TUVX2000\\SQLEXPRESS");
        ds.setPortNumber(1433);
        ds.setDatabaseName("QLCR");
        Connection conn;

        try {
            conn = ds.getConnection();

            Statement statement = conn.createStatement();

         //   statement.execute("INSERT INTO contacts (name,phone,email)" + "VALUES('Ty',02347,'tuhuhu')");

         //   statement.execute("UPDATE contacts SET phone = 02347 WHERE CONVERT(VARCHAR, email) = 'tuhuhu'");

            statement.execute("DELETE  FROM contacts WHERE CONVERT(VARCHAR, email) = 'tuhuhu'");

            statement.execute("SELECT * FROM contacts");
            ResultSet result = statement.getResultSet();
            while (result.next()){
                    System.out.println(result.getString("name")+ " " +
                        result.getInt("phone") + " " +
                        result.getString("EMAIL"));
            }

            statement.close();
            conn.close();
        } catch (SQLException throwables ) {
            throwables.printStackTrace();

        }


    }



    public  void add (int a){
    }

}

