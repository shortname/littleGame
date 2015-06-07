/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

import game.Checker;
import game.Direction;
import game.Player;
import game.useful.Heap;
import game.useful.Possibility;
import java.awt.*;
import java.awt.geom.*;
import java.util.EmptyStackException;
import javax.swing.*;

/**
 *
 * @author pas301
 */
public class Man implements Checker{
    
    private final Player player;
    private final JFrame jf;
    
    public Man(Player player, JFrame jf){
        this.player = player;
        this.jf = jf;
    }
    
    public void paint(Graphics2D g2d, float x, float y, float w, float h){
        g2d.setColor(player.color());
        g2d.fill(new Ellipse2D.Float(x, y, w, h));
    }
    
    public Player player(){
        return player;
    }
    
    public Heap<Possibility> possibilities(final Checker[][] checkers, byte[] xy){
        Heap<Possibility> heap = new Heap<>();
        if(xy[0]+1 < checkers[0].length && xy[1]+1 < checkers[1].length){
            if(checkers[xy[0]+1][xy[1]+1] == null){
                if(player.direction() == Direction.DOWN){
                    byte[] pos = new byte[2];
                    pos[0] = (byte) (xy[0]+1);
                    pos[1] = (byte) (xy[1]+1);
                    heap.insert(new Possibility((byte) 0, pos));
                }
            }else{
                if(checkers[xy[0]+1][xy[1]+1].player() != player){
                    if(xy[0]+2 < checkers[0].length && xy[1]+2 < checkers[1].length){
                        if(checkers[xy[0]+2][xy[1]+2] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (xy[0]+2);
                            pos[1] = (byte) (xy[1]+2);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
            }
        }
        if(xy[0]-1 > -1 && xy[1]+1 < checkers[1].length){
            if(checkers[xy[0]-1][xy[1]+1] == null){
                if(player.direction() == Direction.DOWN){
                    byte[] pos = new byte[2];
                    pos[0] = (byte) (xy[0]-1);
                    pos[1] = (byte) (xy[1]+1);
                    heap.insert(new Possibility((byte) 0, pos));
                }
            }else{
                if(checkers[xy[0]-1][xy[1]+1].player() != player){
                    if(xy[0]-2 > -1 && xy[1]+2 < checkers[1].length){
                        if(checkers[xy[0]-2][xy[1]+2] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (xy[0]-2);
                            pos[1] = (byte) (xy[1]+2);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
            }
        }
        if(xy[0]+1 < checkers[0].length && xy[1]-1 > -1){
            if(checkers[xy[0]+1][xy[1]-1] == null){
                if(player.direction() == Direction.UP){
                    byte[] pos = new byte[2];
                    pos[0] = (byte) (xy[0]+1);
                    pos[1] = (byte) (xy[1]-1);
                    heap.insert(new Possibility((byte) 0, pos));
                }
            }else{
                if(checkers[xy[0]+1][xy[1]-1].player() != player){
                    if(xy[0]+2 < checkers[0].length && xy[1]-2 > -1){
                        if(checkers[xy[0]+2][xy[1]-2] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (xy[0]+2);
                            pos[1] = (byte) (xy[1]-2);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
            }
        }
        if(xy[0]-1 > -1 && xy[1]-1 > -1){
            if(checkers[xy[0]-1][xy[1]-1] == null){
                if(player.direction() == Direction.UP){
                    byte[] pos = new byte[2];
                    pos[0] = (byte) (xy[0]-1);
                    pos[1] = (byte) (xy[1]-1);
                    heap.insert(new Possibility((byte) 0, pos));
                }
            }else{
                if(checkers[xy[0]-1][xy[1]-1].player() != player){
                    if(xy[0]-2 > -1 && xy[1]-2 > -1){
                        if(checkers[xy[0]-2][xy[1]-2] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (xy[0]-2);
                            pos[1] = (byte) (xy[1]-2);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
            }
        }
        return heap;
    }
}
