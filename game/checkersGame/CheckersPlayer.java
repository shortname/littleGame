/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.checkersGame;

import game.AI;
import game.Direction;
import game.Player;
import java.awt.Color;

/**
 *
 * @author JWMD
 */
public class CheckersPlayer implements Player{

    private String name;
    private CheckersAI controller;
    private Color color;
    private Direction direction;
    
    public CheckersPlayer(Color color, String name, Direction direction){
        this.color = color;
        this.controller = null;
        this.name = name;
        this.direction = direction;
    }
    
    public CheckersAI controller() {
        return controller;
    }
    
    public void controller(AI controller) {
        this.controller = (CheckersAI) controller;
    }

    public Color color() {
        return color;
    }

    public String name() {
        return name;
    }
    
    public Direction direction(){
        return direction;
    }
    
}
