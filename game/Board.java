/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.checkersGame.MoveState;
import game.exceptions.CheckerNotFoundException;
import game.exceptions.DisactivatedException;
import game.exceptions.FieldNotFoundException;
import game.exceptions.MultipleMoveException;
import game.exceptions.WrongMoveException;
import java.awt.Graphics2D;

/**
 *
 * @author JWMD
 */
public interface Board {
    public void paint(Graphics2D g2d);
    public void activate(byte[] xy, Player player) throws CheckerNotFoundException;
    public MoveState move(byte[] xy) throws WrongMoveException, DisactivatedException, MultipleMoveException;
    public boolean check(Player player);
    public byte[] onBoard(float xp, float yp) throws FieldNotFoundException;
}
