/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

import game.Board;
import game.Direction;
import game.Game;
import game.Player;
import game.Simulation;
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
    private Board lastBoard;
    private Simulation sim;
    private final Player[] players;
    private byte cPlayer;
    private byte[] checkersOnBoard;
    private boolean activated;
    
    public CheckersGame(boolean p1b, JFrame jf){
        players = new Player[2];
        if(p1b){
            players[0] = new CheckersPlayer(Color.BLACK, "The Blacks", Direction.DOWN);
            players[1] = new CheckersPlayer(Color.WHITE, "The Whites", Direction.UP);
            cPlayer = 1;
        }else{
            players[1] = new CheckersPlayer(Color.BLACK, "The Withes", Direction.UP);
            players[0] = new CheckersPlayer(Color.WHITE, "The Blacks", Direction.DOWN);
            cPlayer = 0;
        }
        board = new CheckersBoard(jf, players[0], players[1]);
        players[0].controller(new CheckersAI(board, players[0], players[1]));
        //players[0].controller(new CheckersAI(board, players[0], players[1]));
        checkersOnBoard = new byte[2];
        checkersOnBoard[0] = 12;
        checkersOnBoard[1] = 12;
        activated = false;
        if(players[cPlayer].controller() != null){
            byte acPlayer = (byte) (cPlayer == 0 ? 1 : 0);
            MoveState ms = players[cPlayer].controller().move(board);
            if(ms == null)
                System.exit(-1);
            board = ms.board;
            checkersOnBoard[acPlayer] -= ms.ownValue;
            cPlayer = acPlayer;
        }
        if(!board.check(players[cPlayer]))
            System.exit(-2);
        sim = new Simulation(board);
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
            MoveState afterMove;
            try{
                afterMove = board.move(xy);
            }catch(WrongMoveException exc){
                System.err.println(exc);
                return null;
            }catch(DisactivatedException exc){
                activated = false;
                return null;
            }catch(MultipleMoveException exc){
                System.err.println(exc);
                return null;
            }
            activated = false;
            checkersOnBoard[acPlayer] -= afterMove.value;
            checkersOnBoard[cPlayer] += afterMove.ownValue;
            System.err.println("\t" + checkersOnBoard[0] + " : " + checkersOnBoard[1]);
            if(checkersOnBoard[acPlayer] == 0){
                return players[cPlayer].name();
            }else{
                cPlayer = acPlayer;
                if(players[cPlayer].controller() != null){
                    acPlayer = (byte) (cPlayer == 0 ? 1 : 0);
                    MoveState ms = players[cPlayer].controller().move(board);
                    if(ms == null)
                        return "No one";
                    lastBoard = board;
                    sim.show((CheckersBoard) board);
                    board = ms.board;
                    checkersOnBoard[acPlayer] -= ms.value;
                    checkersOnBoard[cPlayer] += ms.ownValue;
                    System.err.println("\t" + checkersOnBoard[0] + " : " + checkersOnBoard[1]);
                    if(checkersOnBoard[acPlayer] == 0)
                        return players[cPlayer].name();
                    cPlayer = acPlayer;
                }
                if(!board.check(players[cPlayer]))
                    return "No one";
                return null;
            }
        }
    }
    
    public void paint(Graphics2D g2d){
        board.paint(g2d);
    }
    
}
