/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

import game.AI;
import game.Board;
import game.Direction;
import game.Game;
import game.Player;
import game.exceptions.CheckerNotFoundException;
import game.exceptions.DisactivatedException;
import game.exceptions.FieldNotFoundException;
import game.exceptions.MultipleMoveException;
import game.exceptions.WrongMoveException;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 *
 * @author JWMD
 */
public class CheckersGame implements Game{

    private Board board;
    private final Player[] players;
    private byte cPlayer;
    private byte[] checkersOnBoard;
    private boolean activated;
    
    public CheckersGame(boolean p1b, JFrame jf){
        players = new Player[2];
        if(p1b){
            players[0] = new CheckersPlayer(Color.BLACK, "Player1", Direction.DOWN);
            players[1] = new CheckersPlayer(Color.WHITE, "Player2", Direction.UP);
            cPlayer = 1;
        }else{
            players[1] = new CheckersPlayer(Color.BLACK, "Player1", Direction.UP);
            players[0] = new CheckersPlayer(Color.WHITE, "Player2", Direction.DOWN);
            cPlayer = 0;
        }
        board = new CheckersBoard(jf, players[0], players[1]);
        players[1].controller(new CheckersAI(board, players[1], players[0]));
        //players[0].controller(new CheckersAI(board, players[0], players[1]));
        checkersOnBoard = new byte[2];
        checkersOnBoard[0] = 12;
        checkersOnBoard[1] = 12;
        activated = false;
        if(players[cPlayer].controller() != null){
            byte acPlayer = (byte) (cPlayer == 0 ? 1 : 0);
            board = players[cPlayer].controller().move(board);
            cPlayer = acPlayer;
        }
        board.check(players[cPlayer]);
    }
    
    public String interpret(float x, float y) {
        byte xy[];
        try{
            xy = board.onBoard(x, y);
        }catch(FieldNotFoundException exc){
            System.err.println(exc);
            return null;
        }
        if(!activated){
            try{
                board.activate(xy, players[cPlayer]);
            }catch(CheckerNotFoundException exc){
                System.err.println(exc);
                return null;
            }
            activated = true;
            return null;
        }else{
            byte acPlayer = (byte) (cPlayer == 0 ? 1 : 0);
            byte captured = 0;
            try{
                captured = board.move(xy);
            }catch(WrongMoveException exc){
                System.err.println(exc);
                return null;
            }catch(DisactivatedException exc){
                activated = false;
                return null;
            }catch(MultipleMoveException exc){
                checkersOnBoard[acPlayer]--;
                System.err.println(exc);
                return null;
            }
            System.err.println(captured);
            activated = false;
            checkersOnBoard[acPlayer] -= captured;
            if(checkersOnBoard[acPlayer] == 0){
                return players[cPlayer].name();
            }else{
                cPlayer = acPlayer;
                if(players[cPlayer].controller() != null){
                    board = players[cPlayer].controller().move(board);
                    acPlayer = (byte) (cPlayer == 0 ? 1 : 0);
                    cPlayer = acPlayer;
                }
                board.check(players[cPlayer]);
                return null;
            }
        }
    }
    
    public void paint(Graphics2D g2d){
        board.paint(g2d);
    }
    
}
