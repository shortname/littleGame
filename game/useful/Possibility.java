/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.useful;

import java.util.ArrayList;

/**
 *
 * @author JWMD
 */
public class Possibility implements Comparable<Byte>{

    public byte key;
    public byte[] value;
    
    public Possibility(byte key, byte[] value){
        this.key = key;
        this.value = value;
    }
    
    public Byte key() {
        return key;
    }
    
    public byte[] value(){
        return value;
    }
    
}
