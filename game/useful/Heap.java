/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.useful;
import java.util.ArrayList;
import java.util.EmptyStackException;
/**
 *
 * @author pas301
 */
public class Heap<Z extends Comparable> {
    private ArrayList<Z> values;
    
    public Heap(){
        values = new ArrayList<>();
        values.add(null);
    }
    
    private void replace(int indeks1, int indeks2){
        Z temp = values.get(indeks1);
        values.set(indeks1, values.get(indeks2));
        values.set(indeks2, temp);
    }
    
    private void heapUp(){
        for(int i = values.size()-1; i>1;){
            if(values.get(i).key().doubleValue() > values.get(i/2).key().doubleValue()){
                replace(i, i/2);
                i = i/2;
            }else{
                break;
            }
        }
    }
    
    private void heapDown(){
        int i = 1;
        while(true){
            if(i*2 >= values.size()) break;
            if(values.get(i).key().doubleValue() < values.get(i*2).key().doubleValue()){
                replace(i, i*2);
            }
            if(i*2+1 >= values.size()) break;
            if(values.get(i).key().doubleValue() < values.get(i*2+1).key().doubleValue()){
                replace(i, i*2+1);
            }
            i++;
        }
    }
    
    public void insert(Z value){
        values.add(value);
        heapUp();
    }
    
    public Z get() throws EmptyStackException{
        if(values.size() == 1) throw new EmptyStackException();
        Z toRet = values.get(1);
        int last = values.size()-1;
        Z up = values.get(last);
        values.remove(last);
        if(values.size() > 1){
            values.set(1, up);
            heapDown();
        }
        return toRet;
    }
}
