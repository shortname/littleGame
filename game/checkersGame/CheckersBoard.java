/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;
import game.Board;
import game.Checker;
import game.Direction;
import game.Player;
import game.exceptions.FieldNotFoundException;
import game.exceptions.CheckerNotFoundException;
import game.exceptions.DisactivatedException;
import game.exceptions.MultipleMoveException;
import game.exceptions.WrongMoveException;
import game.useful.Heap;
import game.useful.Possibility;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import javax.swing.*;
/**
 *
 * @author pas301
 */
public class CheckersBoard implements Board{
    
    private Checker[][] checkers;
    private final JFrame jf;
    private final float factor;
    private byte activated[];
    private ArrayList<Possibility> possibilities;
    private ArrayList<byte[]> activationPossibilities;
    private final byte size = 8;
    
    public CheckersBoard(JFrame jf, Player player1, Player player2){
        checkers = new Checker[size][size];
        this.jf = jf;
        factor = 0.8f;
        activated = null;
        activationPossibilities = null;
        for(byte i = 0; i < 3; i++){
            for(byte j = 0; j < size; j++){
                if((i%2==0 && j%2==1) || (i%2==1 && j%2==0)){
                    put(new Man(player1, jf), j, i);
                }
            }
        }
        for(byte i = 5; i < 8; i++){
            for(byte j = 0; j < size; j++){
                if((i%2==0 && j%2==1) || (i%2==1 && j%2==0)){
                    put(new Man(player2, jf), j, i);
                }
            }
        }
        //put(new Man(player2, jf), (byte) 4, (byte) 3);
    }
    
    public CheckersBoard(CheckersBoard cb){
        checkers = new Checker[size][size];
        this.jf = cb.jf;
        factor = 0.8f;
        activated = cb.activated;
        activationPossibilities = cb.activationPossibilities;
        possibilities = cb.possibilities;
        for(byte i = 0; i < cb.size; i++){
            for(byte j = 0; j < cb.size; j++){
                checkers[i][j] = cb.checkers[i][j];
            }
        }
    }
    
    private void multipleSimulation(ArrayList<MoveState> cmsal, byte fResult) throws CheckerNotFoundException, WrongMoveException, DisactivatedException{
        for(Possibility pos : possibilities){
                CheckersBoard copy = new CheckersBoard(this);
                try{
                    copy.move(pos.value);
                }catch(MultipleMoveException exc){
                    copy.multipleSimulation(cmsal, ++fResult);
                }
                MoveState cms = new MoveState(++fResult, copy);
                cmsal.add(cms);
            }
    }
    
    public ArrayList<MoveState> simulate(Player player) throws CheckerNotFoundException, WrongMoveException, DisactivatedException{
        check(player);
        ArrayList<MoveState> cmsal = new ArrayList<>();
        for(byte[] ap : activationPossibilities){
            pActivate(ap, player);
            for(Possibility pos : possibilities){
                CheckersBoard copy = new CheckersBoard(this);
                copy.check(player);
                copy.activate(ap, player);
                byte result = 0;
                boolean added = false;
                try{
                    result = copy.move(pos.value);
                }catch(MultipleMoveException exc){
                    copy.multipleSimulation(cmsal, (byte) 1);
                    added = true;
                }
                if(!added){
                    MoveState cms = new MoveState(result, copy);
                    cmsal.add(cms);
                }
            }
        }
        return cmsal;
    }
    
    public void paint(Graphics2D g2d){
        float x = jf.getWidth(), y = jf.getHeight();
        float a = factor*( x < y ? x : y);
        float x0 = (float) (x-a)/2, y0 = (float) (y-a)/2;
        Color light = new Color(194, 178, 128);
        Color dark = new Color(150, 75, 0);
        g2d.fill(new Rectangle2D.Float(x0, y0, a, a));
        for(byte i = 0; i < size; i++){
            for(byte j = 0; j < size; j++){
                if(i%2==0){
                    if(j%2 == 0){
                        g2d.setColor(light);
                    }else{
                        g2d.setColor(dark);
                    }
                }else{
                    if(j%2 == 0){
                        g2d.setColor(dark);
                    }else{
                        g2d.setColor(light);
                    }
                }
                if(activated!=null)
                    if(activated[0] == i && activated[1] == j)
                        g2d.setColor(Color.YELLOW);
                if(possibilities != null){
                    for(Possibility p : possibilities)
                        if(p.value[0] == i && p.value[1] == j){
                            g2d.setColor(Color.GREEN);
                            break;
                        }
                }
                if(activationPossibilities != null){
                    for(byte[] b : activationPossibilities)
                        if(b[0] == i && b[1] == j){
                            g2d.setColor(Color.ORANGE);
                            break;
                        }
                }
                g2d.fill(new Rectangle2D.Float(x0+i*a/size, y0+j*a/size, a/size, a/size));
                if(checkers[i][j]!=null)
                    checkers[i][j].paint(g2d, x0+i*a/size, y0+j*a/size, a/size, a/size);
            }
        }
    }
    
    public boolean put(Checker checker, byte xp, byte yp){
        if(xp>7 || yp>7 || xp<0 || yp <0)
            return false;
        if(checkers[xp][yp] != null)
            return false;
        checkers[xp][yp] = checker;
        return true;
    }
    
    public byte[] onBoard(float xp, float yp) throws FieldNotFoundException{
        byte[] ret = new byte[2];
        float x = jf.getWidth(), y = jf.getHeight();
        float a = factor*( x < y ? x : y);
        float x0 = (float) (x-a)/2, y0 = (float) (y-a)/2;
        if(xp < x0 || xp > x0+a || yp < y0 || yp > y0+a){
            throw new FieldNotFoundException();
        }
        ret[0] = (byte) ((xp-x0)*size/a);
        ret[1] = (byte) ((yp-y0)*size/a);
        return ret;
    }
    
    public byte move(byte[] xy) throws WrongMoveException, DisactivatedException, MultipleMoveException{
        if(activated != null){
            if(xy[0] == activated[0] && xy[1] == activated[1]){
                activated = null;
                possibilities = null;
                jf.repaint();
                throw new DisactivatedException();
            }
        }
        for(Possibility p : possibilities){
            if(p.value[0] == xy[0] && p.value[1] == xy[1]){
                Checker moved = checkers[activated[0]][activated[1]];
                checkers[activated[0]][activated[1]] = null;
                checkers[xy[0]][xy[1]] = moved;
                if(p.key != 0){
                    byte x = (byte) (xy[0] - Math.abs(xy[0] - activated[0])/(xy[0] - activated[0]));
                    byte y = (byte) (xy[1] - Math.abs(xy[1] - activated[1])/(xy[1] - activated[1]));
                    checkers[x][y] = null;
                    activated = null;
                    possibilities = null;
                    activationPossibilities = null;
                    try{
                        pActivate(xy, moved.player());
                    }catch(CheckerNotFoundException exc){
                        throw new WrongMoveException();
                    }
                    if(possibilities != null){
                        if(!possibilities.isEmpty()){
                            if(possibilities.get(0).key != 0){
                                jf.repaint();
                                throw new MultipleMoveException();
                            }
                        }
                    }
                }
                activated = null;
                possibilities = null;
                if(moved.player().direction() == Direction.UP && xy[1] == 0){
                    checkers[xy[0]][xy[1]] = new King(moved.player(), jf);
                }
                if(moved.player().direction() == Direction.DOWN && xy[1] == size-1){
                    checkers[xy[0]][xy[1]] = new King(moved.player(), jf);
                }
                jf.repaint();
                return p.key;
            }
        }
        throw new WrongMoveException();
    }
    
    private void pActivate(byte[] xy, Player player) throws CheckerNotFoundException{
        Checker chk = checkers[xy[0]][xy[1]];
        if(chk == null)
            throw new CheckerNotFoundException();
        if(chk.player() != player)
            throw new CheckerNotFoundException();
        activated = xy;
        try{
            Heap<Possibility> heap = checkers[xy[0]][xy[1]].possibilities(checkers, xy);
            possibilities = new ArrayList<>();
            final byte best;
            Possibility fpos = heap.get();
            best = fpos.key();
            possibilities.add(fpos);
            while(true){
                Possibility pos = heap.get();
                if(pos.key == best){
                    possibilities.add(pos);
                }
            }
        }catch(EmptyStackException exc){
        }
    }

    public void activate(byte[] xy, Player player) throws CheckerNotFoundException {
        boolean found = false;
        for(byte[] b : activationPossibilities){
            if(b[0] == xy[0] && b[1] == xy[1]){
                found = true;
                break;
            }
        }
        if(!found)
            throw new CheckerNotFoundException();
        pActivate(xy, player);
        jf.repaint();
    }

    public boolean check(Player player){
        byte max = 0;
        activationPossibilities = new ArrayList<>();
        ArrayList<byte[]> cht = new ArrayList<>();
        for(byte i = 0; i < size; i++){
            for(byte j = 0; j < size; j++){
                Checker chk = checkers[i][j];
                if(checkers[i][j] != null){
                    if(checkers[i][j].player() == player){
                        byte[] b = new byte[2];
                        b[0] = i;
                        b[1] = j;
                        try{
                            pActivate(b, player);
                        }catch(CheckerNotFoundException exc){
                            System.err.println(exc);
                            return false;
                        }
                        byte poses;
                        if(possibilities != null){
                            if(!possibilities.isEmpty()){
                                poses = possibilities.get(0).key;
                                if(poses > max){
                                    max = poses;
                                    cht.add(b);
                                }else if(poses == max){
                                    cht.add(b);
                                }
                            }
                        }
                    }
                }
            }
        }
        possibilities = null;
        for(byte[] b : cht){
            try{
                pActivate(b, player);
            }catch(CheckerNotFoundException exc){
                System.err.println(exc);
                return false;
            }
            if(possibilities != null){
                if(!possibilities.isEmpty()){
                    if(possibilities.get(0).key == max){
                        activationPossibilities.add(b);
                    }
                }
            }
        }
        activated = null;
        possibilities = null;
        return !activationPossibilities.isEmpty();
    }
}
