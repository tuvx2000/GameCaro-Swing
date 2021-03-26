package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import ai.AlphaBetaPrunning;
import caro.*;
import util.Function;
import util.ImageCaro;

public class MainView extends JPanel implements MouseListener, MouseMotionListener {

    public int TYPE ;
    public static final int SIZEX = 16;
    public static final int SIZEY = 16;
    public static final int OFFSET = 30;
    private static final int WEIGHT = 600, HEIGHT = 600;
    private static final int SQUAD = 32;
    public static final int DEPTH = 2;
    private static GameState gameState;
    private int victory;
    private int drawX;
    private int drawY;
    public static boolean ok = false;
    public static JButton e =new JButton("Trans Rules ");


    private JPanel p1 = new JPanel(), p2 = new JPanel();
    private JSplitPane jsp;

    public static void main(String[] ags) {
        JFrame frame = new JFrame();
        JPanel pannel = new JPanel();
        pannel.setBackground(Color.red);
        pannel.setBounds(580,0,250,580);

        JButton a =new JButton("Reset");
        a.setBounds(0,100,200,80);
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();
                frame.repaint();

            }
        });
        JButton b=new JButton("Undo");
        b.setBounds(0,200,200,80);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.GoBack();
                frame.repaint();
            }
        });
        JButton c =new JButton("Save");
        c.setBounds(0,300,200,80);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();
                frame.repaint();

            }
        });
        JButton d =new JButton("Resume");
        d.setBounds(0,400,200,80);
        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();
                frame.repaint();

            }
        });
        e.setBounds(0,400,200,80);
        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.Reset();
                frame.repaint();

            }
        });


        JLabel label1 = new JLabel();
        pannel.add(label1);
        label1.setText("Bảng Điều Khiển");
        label1.setBounds(0,0,0,0);





        frame.setTitle("Game Carô");
        frame.setSize(780, 640);
        //frame.setBackground(Color.orange);
        //frame.setOpacity();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(pannel);

        frame.add(new MainView());
        pannel.add(a);
        pannel.add(b);
        pannel.add(c);
        pannel.add(d);


        // pannel.add(new MainView());

    }



    public MainView() {
        this.setBounds(0,0,580,580);
        this.setBackground(Color.yellow);
        gameState = new GameState(Player.BLACK);
        victory = -1;
        gameState.addSquare(new Square(0, 0,true));
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


            System.out.println("Nguoi choi "+ piece.getPlayer().getHashValue()+" :("  + piece.getSquare().getCoordX()+ ";" + piece.getSquare().getCoordY() + ")");
        }
        System.out.println(move.size());





        ///////////////
        /*
        Font defaultFont = g.getFont();
        switch (victory) {
            case 1:
                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
                g.drawString("AI (BLACK) wins!", 50, 50);
                g.setFont(defaultFont);
                break;
            case 2:
                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
                g.drawString("HUMAN (WHITE) wins!", 50, 50);
                g.setFont(defaultFont);
                break;
            case 0:
                g.setFont(new Font(defaultFont.getFontName(), 1, 30));
                g.drawString("DRAW!", 50, 50);
                g.setFont(defaultFont);
                break;
            default:
                break;
        }*/

        ///////////////
    }






    @Override
    public void mousePressed(MouseEvent e) {
        int coordX = (e.getX() - OFFSET) / SQUAD;
        int coordY = (e.getY() - OFFSET) / SQUAD;
        System.out.println(coordX + " " + coordY);
        Square square = new Square(coordX, coordY,true);


        if (gameState.getCurrentPlayer() == Player.WHITE && e.getButton() == 1) {
            int[][] board = gameState.getBoard();
            if (board[coordX][coordY] == 0) {
                gameState.addSquare(square);/**
                Pair pair = AlphaBetaPrunning.search(gameState);
                if (pair.getSquare() == null) {
                    if (Function.evaluate(gameState, Player.WHITE) >= 10000) {
                        victory = 2;
                    } else {
                        victory = 0;
                    }
                } else {
                    gameState.addSquare(pair.getSquare());
                    if (Function.evaluate(gameState, Player.BLACK) >= 10000) {
                        victory = 1;
                    }
                }*/
            }
        }else if (gameState.getCurrentPlayer() == Player.BLACK && e.getButton() == 1){
            int[][] board = gameState.getBoard();
            if (board[coordX][coordY] == 0) {
                gameState.addSquare(square);}

        }



        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        repaint();

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
