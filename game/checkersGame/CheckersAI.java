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
    private MoveState result;
    private final byte maxDepth = 2;
    public static final byte kingsBonus = 10;
    
    public CheckersAI(Board board, Player player, Player opponent){
        this.board = board;
        this.player = player;
        this.opponent = opponent;
        result = null;
    }
    
    private void postOrder(Tree<MoveState>.Element ancestor, boolean max){
        byte extremum = max ? Byte.MIN_VALUE : Byte.MAX_VALUE;
        for(Tree<MoveState>.Element el : ancestor.kids()){
            postOrder(el, !max);
            if(max)
                extremum = el.value().simValue > extremum ? el.value().simValue : extremum;
            else
                extremum = el.value().simValue < extremum ? el.value().simValue : extremum;
        }
        if(ancestor.kids().size() != 0)
            ancestor.value().simValue = extremum;
    }
    
    private void simulate(Tree<MoveState>.Element ancestor, byte depth, Player pPlayer){
        byte rdepth = depth;
        if(result != null || depth == 0) return;
        try{
            ArrayList<MoveState> cmsal = ancestor.value().board.simulate(pPlayer);
            for(MoveState ms : cmsal){
                byte weight = (byte) ((pPlayer == player ? (byte) 1 : (byte) -1)*ms.value + ancestor.value().simValue);
                weight += ms.ownValue;
                ms.simValue = weight;
                //new Simulation(ms, rdepth);
                Tree<MoveState>.Element mse = tree.insert(ancestor, ms);
                Player ppl = pPlayer == player ? opponent : player;
                simulate(mse, (byte) (depth-1), ppl);
            }
        }catch(CheckerNotFoundException | WrongMoveException | DisactivatedException exc){
            System.err.println(exc);
        }
    }
    
    public MoveState move(Board start){
        board = start;
        tree = new Tree<>(new MoveState( (byte) 0, (byte) 0, new CheckersBoard((CheckersBoard) board)));
        root = tree.root();
        result = null;
        simulate(root, maxDepth, player);
        postOrder(root, true);
        System.err.println(tree);
        for(Tree<MoveState>.Element e : root.kids()){
            if(e.value().simValue == root.value().simValue){
                result = e.value();
                break;
            }
        }
        return result;
    }
    
}
