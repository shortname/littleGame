/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.exceptions;

/**
 *
 * @author pas301
 */
public class FieldNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>FieldNotFoundException</code> without detail message.
     */
    public FieldNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>FieldNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FieldNotFoundException(String msg) {
        super(msg);
    }
}
