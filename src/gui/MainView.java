package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import javax.swing.*;

import ai.AlphaBetaPrunning;
import caro.*;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import util.Function;

public class MainView extends JPanel implements MouseListener, MouseMotionListener {

    public static int TYPE = 0;/// type of playerssss
    public static final int SIZEX = 16;
    public static final int SIZEY = 16;
    public static final int OFFSET = 30;
    private static final int SQUAD = 32;
    private static GameState gameState;
    private int victory= -1;


    private JPanel p1 = new JPanel(), p2 = new JPanel();
    private JSplitPane jsp;

    public static void main(String[] ags) {
        JFrame frame = new JFrame();
        JPanel pannel = new JPanel();
        JPanel bottomPannel = new JPanel();

////////////////////////////////////// this is a DB




        ////////////////////////////


        bottomPannel.setBackground(Color.gray);
        bottomPannel.setBounds(0,580,580+200,30);
        pannel.setBackground(Color.red);
        pannel.setBounds(580,0,200,580);

        /////////////////////// xong roi

        JButton a =new JButton("Reset");
        a.setBounds(0,100,200,80);
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();
                if (TYPE == 0){
                    gameState = new GameState(Player.OPLAYER);
                    Random random = new Random();
                    gameState.addSquare(new Square( random.nextInt(SIZEX), random.nextInt(16),true));
                }
                frame.repaint();

            }
        });
        /////////////////////// xong roi
        JButton b=new JButton("Undo");
        b.setBounds(0,200,200,80);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.GoBack();
                if(TYPE == 0)
                    gameState.GoBack();
                frame.repaint();
            }
        });



        JButton c =new JButton("Save");
        c.setBounds(0,300,200,80);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ////////
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


                    //   statement.execute("UPDATE contacts SET phone = 02347 WHERE CONVERT(VARCHAR, email) = 'tuhuhu'");

                    //statement.execute("DELETE  FROM contacts WHERE CONVERT(VARCHAR, email) = 'tuhaha'");

                    statement.execute("DELETE  FROM Square");
                    statement.execute("DELETE  FROM SavedGame");


                    ArrayList<Piece> move = gameState.getMove();

                    statement.execute("INSERT INTO SavedGame (type)" +
                            "VALUES("+TYPE+")");



                    for (int i = 0 ; i < move.size() ; i++){
                        int hold = (move.get(i).getSquare().isMark() == true )? 1 : 0;
                        statement.execute("INSERT INTO Square (x,y,isMark)" +
                              "VALUES("
                            +move.get(i).getSquare().getCoordX()+","+
                            move.get(i).getSquare().getCoordY()+","+
                                hold+")");
                    }

                    statement.close();
                    conn.close();
                } catch (SQLException throwables ) { throwables.printStackTrace(); }
                /////////

            }
        });
        JButton d =new JButton("Resume");
        d.setBounds(0,400,200,80);
        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();


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



                    statement.execute("SELECT * FROM Square");
                    ResultSet result = statement.getResultSet();
                    int j = 0;
                    while (result.next()){
                        j++;
                        Player hold0 = (j % 2 == 1) ? Player.OPLAYER : Player.XPLAYER;
                        Square hold1 = new Square(result.getInt("x"),result.getInt("y"),
                                (result.getInt("isMark") == 0) ? false : true);
              //          System.out.println(hold1.isMark());
                        Piece hold2 = new Piece(hold1,hold0);
                        gameState.setCurrentPlayer(hold0);
                        gameState.addSquare(hold1);
                //        System.out.println(hold0);
                    }

                    gameState.setCurrentPlayer((gameState.getMove().size() % 2 == 0) ? Player.OPLAYER:Player.XPLAYER);



                    ResultSet typeGame = statement.executeQuery("SELECT * FROM SavedGame");
                    typeGame.next();
                    TYPE = typeGame.getInt("TYPE");


               //     System.out.println(j);




                    statement.close();
                    conn.close();
                } catch (SQLException throwables ) { throwables.printStackTrace(); }

                frame.repaint();


            }

        });




        /////////////////////// xong roi
        JButton e =new JButton("Chang Mode");
        e.setBounds(0,480,200,40);
        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();
                TYPE = ((TYPE == 0) ? 1 : 0);
                if (TYPE == 0){
                    gameState = new GameState(Player.OPLAYER);
                    Random random = new Random();
                    gameState.addSquare(new Square( random.nextInt(SIZEX), random.nextInt(SIZEX),true));
                }
                frame.repaint();

            }
        });



        JLabel label1 = new JLabel();

        label1.setText("Panel:");

        label1.setBounds(50,0,50,50);
        label1.setSize(50,100);





        JLabel label2 = new JLabel();
        label2.setText("Designed by 10Real!");
        label2.setBounds(0,0,0,0);
        bottomPannel.add(label2);






        frame.setTitle("Game Carô");
        frame.setSize(780, 640);
        //frame.setBackground(Color.orange);
        //frame.setOpacity();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(bottomPannel);

        frame.add(pannel);


        frame.add(new MainView());
        pannel.add(a);
        pannel.add(b);
        pannel.add(c);
        pannel.add(d);
        pannel.add(e);
        pannel.add(label1);




        // pannel.add(new MainView());

    }



    public MainView() {
        this.setBounds(0,0,580,580);
        this.setBackground(Color.yellow);
        gameState = new GameState(Player.OPLAYER);
        Random random = new Random();
        gameState.addSquare(new Square( random.nextInt(SIZEX), random.nextInt(SIZEX),true));
        addMouseMotionListener(this);
        addMouseListener(this);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= SIZEX; i++) {
            g.drawLine(SQUAD * i + OFFSET, OFFSET, SQUAD * i + OFFSET, SQUAD * SIZEY + OFFSET);
        }
        for (int i = 0; i <= SIZEY; i++) {
            g.drawLine(OFFSET, SQUAD * i + OFFSET, SQUAD * SIZEX + OFFSET, SQUAD * i + OFFSET);
        }





        ArrayList<Piece> move = gameState.getMove();
        for (int i = 0; i < move.size(); i++) {
            Piece piece = move.get(i);
            g.drawImage(piece.getPlayer().getImg(), SQUAD * piece.getSquare().getCoordX() + OFFSET,
                    SQUAD * piece.getSquare().getCoordY() + OFFSET, this);


         //   System.out.println("Nguoi choi "+ piece.getPlayer().getHashValue()+" :("  + piece.getSquare().getCoordX()+ ";" + piece.getSquare().getCoordY() + ")");
        }
    //    System.out.println(move.size());





        ///////////////

        Font defaultFont = g.getFont();
        switch (victory) {
            case 1:
                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
                g.drawString("Player 1 WINS!",580/2, 570);
                try {
                    gameState.setCurrentPlayer(null);
                }catch (Exception e){
                    System.out.println("Something went 1!!!!!!!!");
                }
                g.setFont(defaultFont);
                victory = -1;
                break;
            case 2:
                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
                g.drawString("Player 2 WINS!", 580/2, 570);
                try {
                    gameState.setCurrentPlayer(null);
                }catch (Exception e){
                    System.out.println("Something wrong 2!!!!!!!!!");
                }
                g.setFont(defaultFont);
                victory = -1;
                break;
//            case 0:
//                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
//                g.drawString("Game is ENDED!", 580/2, 570);
//                g.setFont(defaultFont);
//                victory = -1;
//                break;
            default:
                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
                g.drawString("Playing Mode:" + ((TYPE == 1)? "Human" : "Computer"), 380/2, 570);
                g.setFont(defaultFont);

                break;
        }



        ///////////////
    }






    @Override
    public void mousePressed(MouseEvent e) {
        int coordX = (e.getX() - OFFSET) / SQUAD;
        int coordY = (e.getY() - OFFSET) / SQUAD;
        System.out.println(coordX + " " + coordY + " " + gameState.getCurrentPlayer().toString());
        Square square = new Square(coordX, coordY,true);


        if (gameState.getCurrentPlayer() == Player.XPLAYER && e.getButton() == 1) {
            int[][] board = gameState.getBoard();
            if (board[coordX][coordY] == 0) {
                gameState.addSquare(square);

                checkwin();

                 //////////////////////////////////////////////////////////
                if (TYPE == 0){
                    Pair pair = AlphaBetaPrunning.search(gameState);
                    if (pair.getSquare() == null) {
                        if (Function.evaluate(gameState, Player.XPLAYER) >= 10000) {
                            victory = 2;
                        } else {
                            victory = 0;
                        }
                    } else {
                        gameState.addSquare(pair.getSquare());
                        if (Function.evaluate(gameState, Player.OPLAYER) >= 10000) {
                            victory = 1;
                        }
                    }
                }
                /////////////////////////////////////////////////////////////
            }
        }else if (gameState.getCurrentPlayer() == Player.OPLAYER && e.getButton() == 1 && TYPE == 1){
            int[][] board = gameState.getBoard();
            if (board[coordX][coordY] == 0) {
                gameState.addSquare(square);
                checkwin();

            }

        }



        repaint();
    }

    public void checkwin(){

        if (Function.evaluate(gameState, Player.XPLAYER) >= 10000) {
            victory = 2;
        }
        if (Function.evaluate(gameState, Player.OPLAYER) >= 10000) {
            victory = 1;
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
