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
public class DisactivatedException extends Exception {

    /**
     * Creates a new instance of <code>DisactivatedException</code> without
     * detail message.
     */
    public DisactivatedException() {
    }

    /**
     * Constructs an instance of <code>DisactivatedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DisactivatedException(String msg) {
        super(msg);
    }
}
