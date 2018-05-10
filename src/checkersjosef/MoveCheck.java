/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import java.util.ArrayDeque;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author josefbenassi
 */
    public class MoveCheck {
  
 
    // method taking in user input rowfrom columnfrom rowto column to  gradient  = 1 see moveDirectionOk methods
    public static float gradient(float x1, float y1, float x2, float y2)
    { 
       return ((y2-y1)/(x2-x1));
    }
    // takes column number states if it even true or false
    public static boolean isEven(int j) {
         return j % 2 == 0;
    }
   // if odd if its not even method
    public static boolean isOdd(int j) {
         return !isEven(j);
    }
    // takes in move from input depending on move from string will -2 or +2 to row and column value
    public static String plusOne(String moveFrom, String moveTo)
    {
        
       String[] frmXY = moveFrom.split(",");
       String[] toXY = moveTo.split(",");
       
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
       int x2 = Integer.parseInt(toXY[0]);
       int y2 = Integer.parseInt(toXY[1]);
       
       int nextX = (x2-x1)+ x2;
       int nextY = (y2-y1)+ y2;
       
       return nextX+","+nextY;
 
    }
 
    //  checks if a value is a paticular vectot
    public static boolean isInVector(Vector vectorToTestAgainst, String valueToTestFor)
    {
        return vectorToTestAgainst.contains(valueToTestFor);
    }
    // taking the users input string from and to if moving red, row and has to be greater in the (from move) than the (to move) apart from kings
    public static boolean isRedDirectionOk(String moveFrom, String moveTo) {
        String[] frmXY = moveFrom.split(",");
        String[] toXY = moveTo.split(",");
        float g = gradient(Float.parseFloat(frmXY[0]),Float.parseFloat(frmXY[1]),Float.parseFloat(toXY[0]),Float.parseFloat(toXY[1]));
        if(isInVector(Board.kings,moveFrom))
            return g==1.0 || g==-1.0;    
        else
           return (g==1.0 || g==-1.0) && (Integer.parseInt(frmXY[0])> Integer.parseInt(toXY[0])); 
        
    }
    // taking the users input string (from and to) if moving balck, row and has to be less in the (from move) than the (to move) apart from kings
    public static boolean isBlackDirectionOk(String moveFrom, String moveTo) {
        String[] frmXY = moveFrom.split(",");
        String[] toXY = moveTo.split(",");
        float g = gradient(Float.parseFloat(frmXY[0]),Float.parseFloat(frmXY[1]),Float.parseFloat(toXY[0]),Float.parseFloat(toXY[1]));
        if(isInVector(Board.kings,moveFrom))
            return g==1.0 || g==-1.0;    
        else
           return (g==1.0 || g==-1.0) && (Integer.parseInt(frmXY[0])< Integer.parseInt(toXY[0])); 
        
    } 


   // this method is called we create a stack. for every move in recorded moves vector add the move to the stack
    public static void undoLastMove() { // used in play game class
         ArrayDeque<String> stack = new ArrayDeque<>();   
              
        for(String move : Board.recordedMoves)
        {
            
            if(move.startsWith((PlayGame.playCount)+"::"))
                stack.add(move);
          
        }
        executeUndo(stack);
       
    }
  
    public static  void redoLastUndo() { // used in PLayGame class adn Record class
        Stack<String> stack = new Stack<>();        
        Board.recordedMoves.stream().filter((String move) -> (move.startsWith((PlayGame.playCount)+"::"))).forEachOrdered((String move) -> {
            stack.addElement(move);
     });
        executeRedo(stack);
    }
    
    // takes in a ArrayDeque stack 
    public static void executeUndo(ArrayDeque stack){// used in Play game class and Record class
        
        while(!stack.isEmpty())
        {
            String action = (String)stack.poll();// the queue version of pop is poll. poll from the array deque pass in
            if(action.contains("::add::"))
                action = action.replace("::add::", "::remove::");
            else if(action.contains("::remove::"))
                action = action.replace("::remove::", "::add::");
            
            executeAction(action);
        }
    }
   
    public static void executeRedo(Stack stack){
        //ToDo: do the instruction
         while(!stack.isEmpty())
            executeAction((String)stack.pop());
    }  
        
    public static void executeAction(String action)
    {
        System.out.println(action);
        int pos = action.lastIndexOf("::");
        String val = action.substring(pos+2, action.length());
        if(action.contains("::black::add::")){Board.black.addElement(val); return;}          
        if(action.contains("::white::add::")){Board.red.addElement(val);return;}           
        if(action.contains("::invalid::add::")){Board.invalid.addElement(val);return;}        
        if(action.contains("::vacant::add::")){Board.vacant.addElement(val);return;}         
        if(action.contains("::occupied::add::")){Board.occupied.addElement(val);return;}        
        if(action.contains("::blackCaptures::add::")){Board.blackCaptures.addElement(val);return;}     
        if(action.contains("::whiteCaptures::add::")){Board.redCaptures.addElement(val);return;}   
        if(action.contains("::kings::add::")){Board.kings.addElement(val);return;}  
        if(action.contains("::black::remove::")){Board.black.removeElement(val);return;}            
        if(action.contains("::white::remove::")){Board.red.removeElement(val);return;}           
        if(action.contains("::invalid::remove::")){Board.invalid.removeElement(val);return;}        
        if(action.contains("::vacant::remove::")){Board.vacant.removeElement(val);return;}         
        if(action.contains("::occupied::remove::")){Board.occupied.removeElement(val);return;}        
        if(action.contains("::blackCaptures::remove::")){Board.blackCaptures.removeElement(val);return;}     
        if(action.contains("::whiteCaptures::remove::")){Board.redCaptures.removeElement(val);return;}   
        if(action.contains("::kings::remove::")){Board.kings.removeElement(val);return;}  
    }
     
    
    
    
    
    
}
