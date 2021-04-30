import caro.GameState;
import gui.MainView;
import org.sqlite.SQLiteJDBCLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class Main {
    public static void main (String[] args){
/////test


        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:dbDB.db");
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("something went wrong!: " + e.getMessage());
        }

       // SQLServerDataSource ds = new SQLServerDataSource();





  /////  new Wellcome();

    }

}
