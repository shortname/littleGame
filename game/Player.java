/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;

/**
 *
 * @author JWMD
 */
public interface Player {
    public String name();
    public AI controller();
    public void controller(AI controller);
    public Color color();
    public Direction direction();
}
