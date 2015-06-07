/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

/**
 *
 * @author JWMD
 */
public class MoveState {
    public Byte value;
        public CheckersBoard board;
    
        public MoveState(Byte t, CheckersBoard u){
            this.value = t;
            this.board = u;
        }
}
