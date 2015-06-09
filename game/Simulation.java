/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.checkersGame.MoveState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author JWMD
 */
public class Simulation extends JFrame{
    
    private Board board;
    
    public Simulation(MoveState ms, byte depth){
        this.board = ms.board;
        setSize(500, 500);
        setTitle("Sim " + depth + " " + ms.value);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void paint(Graphics g){
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        Rectangle2D background = new Rectangle2D.Float(0, 0, getWidth(), getHeight());
        g2d.setColor(new Color(255, 255, 255));
        g2d.fill(background);
        board.paint(g2d);
        g.drawImage(image, 0, 0, null);
    }
}
