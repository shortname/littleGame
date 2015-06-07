package game.useful;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author JWMD
 */
public class Tree<T> {
    
    private Element root;
    
    public class Element{
        private T value;
        private ArrayList<Element> next;
        
        private Element(T value){
            this.value = value;
            next = new ArrayList<Element>();
        }
        
        public T value(){
            return value;
        }
        
        public void value(T value){
            this.value = value;
        }
        
        public ArrayList<Element> kids(){
            return new ArrayList<Element>(next);
        }
    }
    
    public Tree(T value){
        root = new Element(value);
    }
    
    public Element root(){
        return root;
    }
    
    public Element insert(Element ancestor, T value){
        Element element = new Element(value);
        ancestor.next.add(element);
        return element;
    }
    
    private class Stringer{
        public String string;
    }
    
    private void toString(Element ancestor, Stringer stringer){
        stringer.string += ancestor.value + " ( " ;
        for(Element kid : ancestor.kids()){
            toString(kid, stringer);
        }
        stringer.string += " ), ";
    }
    
    public String toString(){
        Stringer stringer = new Stringer();
        stringer.string = "";
        toString(root, stringer);
        return stringer.string;
    }
    
    /*public static void main(String[] args) {
        Tree<String> tree = new Tree<>("Grand grandfaher");
        Tree<String>.Element granny = tree.insert(tree.root(), "Grandma");
        Tree<String>.Element grandpa = tree.insert(tree.root(), "Grandpa");
        Tree<String>.Element father = tree.insert(granny, "Daddy");
        Tree<String>.Element mother  = tree.insert(grandpa, "Mommy");
        Tree<String>.Element uncle = tree.insert(grandpa, "Uncle Ben");
        Tree<String>.Element me = tree.insert(father, "Me");
        System.err.println(tree);
    }*/
    
    /*private boolean remove(Element element, Element start){
        for(Element e : start.next){
            if(e == element){
                start.next.remove(e);
                return true;
            }else{
                remove(element, e);
            }
        }
        return false;
    }
    
    public void remove(Element removed) throws ElementNotFoundException{
        if(!remove(removed, root))
            throw new ElementNotFoundException();
    }*/
    
}
