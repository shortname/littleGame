/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import game.useful.Heap;
import game.useful.Possibility;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author pas301
 */
public interface Checker {
    public void paint(Graphics2D g2d, float x, float y, float w, float h);
    public Player player();
    public Heap<Possibility> possibilities(final Checker[][] checkers, byte[] xy);
}
