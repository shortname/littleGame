/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics2D;

/**
 *
 * @author JWMD
 */
public interface Game {
    public String interpret(float x, float y);
    public void paint(Graphics2D g2d);
}
