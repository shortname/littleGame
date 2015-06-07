/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

import game.Checker;
import game.Direction;
import game.Player;
import game.useful.Heap;
import game.useful.Possibility;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;

/**
 *
 * @author JWMD
 */
public class King implements Checker{
    private final Player player;
    private final JFrame jf;
    
    public King(Player player, JFrame jf){
        this.player = player;
        this.jf = jf;
    }
    
    public void paint(Graphics2D g2d, float x, float y, float w, float h){
        g2d.setColor(player.color());
        g2d.fill(new Arc2D.Float(x, y, w, h, 135, 180, Arc2D.Float.PIE));
    }
    
    public Player player(){
        return player;
    }
    
    public Heap<Possibility> possibilities(final Checker[][] checkers, byte[] xy){
        Heap<Possibility> heap = new Heap<>();
        byte x = xy[0], y = xy[1];
        while(++x < checkers.length && ++y < checkers.length){
            if(checkers[x][y] == null){
                byte[] pos = new byte[2];
                pos[0] = (byte) (x);
                pos[1] = (byte) (y);
                heap.insert(new Possibility((byte) 0, pos));
            }else{
                if(checkers[x][y].player() != player){
                    if(++x < checkers[0].length && ++y < checkers[1].length){
                        if(checkers[x][y] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (x);
                            pos[1] = (byte) (y);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
                break;
            }
        }
        x = xy[0];
        y = xy[1];
        while(--x > -1 && ++y < checkers.length){
            if(checkers[x][y] == null){
                byte[] pos = new byte[2];
                pos[0] = (byte) (x);
                pos[1] = (byte) (y);
                heap.insert(new Possibility((byte) 0, pos));
            }else{
                if(checkers[x][y].player() != player){
                    if(--x > -1 && ++y < checkers[1].length){
                        if(checkers[x][y] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (x);
                            pos[1] = (byte) (y);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
                break;
            }
        }
        x = xy[0];
        y = xy[1];
        while(++x < checkers[0].length && --y > -1){
            if(checkers[x][y] == null){
                byte[] pos = new byte[2];
                pos[0] = (byte) (x);
                pos[1] = (byte) (y);
                heap.insert(new Possibility((byte) 0, pos));
            }else{
                if(checkers[x][y].player() != player){
                    if(++x < checkers[0].length && --y > -1){
                        if(checkers[x][y] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (x);
                            pos[1] = (byte) (y);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
                break;
            }
        }
        x = xy[0];
        y = xy[1];
        while(--x > -1 && --y > -1){
            if(checkers[x][y] == null){
                byte[] pos = new byte[2];
                pos[0] = (byte) (x);
                pos[1] = (byte) (y);
                heap.insert(new Possibility((byte) 0, pos));
            }else{
                if(checkers[x][y].player() != player){
                    if(--x > -1 && --y > -1){
                        if(checkers[x][y] == null){
                            byte[] pos = new byte[2];
                            pos[0] = (byte) (x);
                            pos[1] = (byte) (y);
                            heap.insert(new Possibility((byte) 1, pos));
                        }
                    }
                }
                break;
            }
        }
        return heap;
    }
}
