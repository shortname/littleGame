/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.exceptions;

/**
 *
 * @author JWMD
 */
public class MultipleMoveException extends Exception {

    /**
     * Creates a new instance of <code>MultipleMoveException</code> without
     * detail message.
     */
    public MultipleMoveException() {
    }

    /**
     * Constructs an instance of <code>MultipleMoveException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MultipleMoveException(String msg) {
        super(msg);
    }
}
