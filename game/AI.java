/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.checkersGame.MoveState;

/**
 *
 * @author JWMD
 */
public interface AI {
    public MoveState move(Board start);
}
