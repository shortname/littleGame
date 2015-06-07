/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

import game.AI;
import game.Board;
import game.Player;
import game.exceptions.CheckerNotFoundException;
import game.exceptions.DisactivatedException;
import game.exceptions.WrongMoveException;
import game.useful.Tree;
import java.util.ArrayList;

/**
 *
 * @author JWMD
 */
public class CheckersAI implements AI{
    
    private Board board;
    private Player player;
    private Player opponent;
    private Tree<MoveState> tree;
    private Tree<MoveState>.Element root;
    private Board result;
    private final byte maxDepth = 3;
    
    public CheckersAI(Board board, Player player, Player opponent){
        this.board = board;
        this.player = player;
        this.opponent = opponent;
        result = null;
    }
    
    private void postOrder(Tree<MoveState>.Element ancestor, boolean max){
        byte extremum = 0;
        for(Tree<MoveState>.Element el : ancestor.kids()){
            postOrder(el, !max);
            if(max)
                extremum = el.value().value > extremum ? el.value().value : extremum;
            else
                extremum = el.value().value < extremum ? el.value().value : extremum;
        }
        if(!ancestor.kids().isEmpty())
            ancestor.value().value = extremum;
    }
    
    private void simulate(Tree<MoveState>.Element ancestor, byte depth, Player pPlayer){
        if(result != null) return;
        if(depth == 0){
            postOrder(root, true);
            for(Tree<MoveState>.Element e : root.kids()){
                if(e.value().value == root.value().value){
                    result = e.value().board;
                    break;
                }
            }
        }
        try{
            ArrayList<MoveState> cmsal = ancestor.value().board.simulate(pPlayer);
            for(MoveState ms : cmsal){
                byte weight = (byte) ((pPlayer == player ? (byte) 1 : (byte) -1)*ms.value + ancestor.value().value);
                ms.value = weight;
                Tree<MoveState>.Element mse = tree.insert(ancestor, ms);
                Player ppl = pPlayer == player ? opponent : player;
                simulate(mse, --depth, ppl);
            }
        }catch(CheckerNotFoundException | WrongMoveException | DisactivatedException exc){
            System.err.println(exc);
        }
    }
    
    public Board move(Board start){
        board = start;
        tree = new Tree<>(new MoveState( (byte) 0, new CheckersBoard((CheckersBoard) board)));
        root = tree.root();
        result = null;
        simulate(root, maxDepth, player);
        return result;
    }
    
}
