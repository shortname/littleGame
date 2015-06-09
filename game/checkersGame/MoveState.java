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
    public Byte ownValue;
    public CheckersBoard board;

    public MoveState(Byte value, CheckersBoard board){
        this.value = value;
        this.ownValue = value;
        this.board = board;
    }
        
    public String toString(){
        return "" + value; 
    }    
}
