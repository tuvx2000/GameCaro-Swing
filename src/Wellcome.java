import gui.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wellcome extends JFrame {
    Wellcome(){
        JButton button1 = new JButton();
        button1.setBounds(100,100,200,50);
        button1.setText("Choi 2 nguoi");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themv(1);

            }
        });

        JButton button2 = new JButton();
        button2.setBounds(100,250,200,50);
        button2.setText("Choi 1 nguoi");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themv(2);
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,500);
        this.setVisible(true);
        this.add(button1);
        this.add(button2);

    }


    private void themv(int a){
        JFrame x = new JFrame();
        x.add(new MainView());
        x.setSize(600,600);
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
