/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import game.checkersGame.CheckersBoard;
import game.checkersGame.CheckersBoard;
import game.checkersGame.CheckersGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
/**
 *
 * @author pas301
 */
public class GUI extends JFrame{
    
    private final short x=500, y=500;
    private Game game;
    private final JFrame jf = this;
    private JMenuBar jmb;
    private JMenu jm, ng;
    private JMenuItem nsg, nmg, lv; 
    private MouseListener ml = new MouseListener(){

        public void mouseClicked(MouseEvent e) {
            String name = game.interpret(e.getX(), e.getY());
            if(name != null){
                JOptionPane.showMessageDialog(jf, name + " has won!", "Game over", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
        
    };
    
    public GUI(){
        game = new CheckersGame(true, jf);
        setSize(x, y);
        setTitle("Checkers v.1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addMouseListener(ml);
    }
    
    public void paint(Graphics g){
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        Rectangle2D background = new Rectangle2D.Float(0, 0, getWidth(), getHeight());
        g2d.setColor(new Color(0, 100, 0));
        g2d.fill(background);
        game.paint(g2d);
        g.drawImage(image, 0, 0, null);
    }
    
    public static void main(String[] args){
        new GUI();
    }
}
