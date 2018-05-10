/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import java.io.IOException;

/**
 *
 * @author josefbenassi
 */
public class Red {

//method for moving red checkers, adds and removes from vectors based upon the movefrom and moveto
public static void moveRed(int playCount, String moveFrom, String moveTo) throws IOException
    {
        boolean isKing = Board.kings.contains(moveFrom);
        
        if(MoveCheck.isInVector(Board.red,moveFrom) && MoveCheck.isInVector(Board.vacant,moveTo) && !MoveCheck.isInVector(Board.invalid,moveTo) && MoveCheck.isRedDirectionOk(moveFrom,moveTo))
        {          
            Board.red.removeElement(moveFrom);
            Board.auditMove(playCount+"::white::remove::"+ moveFrom);
            
            if(isKing)
            { 
                Board.kings.removeElement(moveFrom);
                Board.auditMove(playCount+"::kings::remove::"+ moveFrom);
            }
           
            Board.occupied.removeElement(moveFrom);
            Board.auditMove(playCount+"::occupied::remove::"+ moveFrom);
            
            Board.vacant.removeElement(moveTo);
            Board.auditMove(playCount+"::vacant::remove::"+ moveTo);
            
            Board.vacant.addElement(moveFrom);
            Board.auditMove(playCount+"::vacant::add::"+ moveFrom);
            
            Board.occupied.addElement(moveTo);
            Board.auditMove(playCount+"::occupied::add::"+ moveTo);
            
            Board.red.addElement(moveTo);
            Board.auditMove(playCount+"::white::add::"+ moveTo);
            
            if(isKing)
            { 
                Board.kings.addElement(moveTo);
                Board.auditMove(playCount+"::kings::add::"+ moveTo);
            }
          
            createARedKing(playCount,moveTo);
            
        }  
        
        else{
           
           System.out.println("wrong move: next players turn");
        }
       //method for moving black checkers, adds and removes from vectors based upon the movefrom and moveto for black jump
       if(MoveCheck.isInVector(Board.red,moveFrom) && MoveCheck.isInVector(Board.occupied,moveTo) && MoveCheck.isInVector(Board.black,moveTo) && MoveCheck.isInVector(Board.vacant,MoveCheck.plusOne(moveFrom,moveTo)) && !MoveCheck.isInVector(Board.invalid,MoveCheck.plusOne(moveFrom,moveTo)) && MoveCheck.isRedDirectionOk(moveFrom,moveTo))
        {   
        
            Board.red.removeElement(moveFrom);
            Board.auditMove(playCount+"::white::remove::"+ moveFrom);
            
            if(isKing)
            { 
                Board.kings.removeElement(moveFrom);
                Board.auditMove(playCount+"::kings::remove"+ moveFrom);
            }
           
            Board.occupied.removeElement(moveTo);
            Board.auditMove(playCount+"::occupied::remove::"+ moveTo);
            
            Board.black.removeElement(moveTo);
            Board.auditMove(playCount+"::black::remove::"+ moveTo);
            
            Board.blackCaptures.addElement(moveTo);
            Board.auditMove(playCount+"::blackCaptures::add::"+ moveTo);
            
            Board.vacant.addElement(moveTo);
            Board.auditMove(playCount+"::vacant::add::"+ moveTo);
            
            Board.vacant.addElement(moveFrom);
            Board.auditMove(playCount+"::vacant::add::"+ moveFrom);
            
            Board.occupied.addElement(MoveCheck.plusOne(moveFrom,moveTo));
            Board.auditMove(playCount+"::occupied::add::"+ MoveCheck.plusOne(moveFrom,moveTo));
            
            Board.red.addElement(MoveCheck.plusOne(moveFrom,moveTo));
            Board.auditMove("white::add::"+ MoveCheck.plusOne(moveFrom,moveTo));
             
            Board.vacant.removeElement(MoveCheck.plusOne(moveFrom,moveTo));
            Board.auditMove("vacant::remove::"+ MoveCheck.plusOne(moveFrom,moveTo));
            
            
            if(isKing)
            {
                Board.kings.addElement(MoveCheck.plusOne(moveFrom,moveTo));
                Board.auditMove(playCount+"::kings::add"+ MoveCheck.plusOne(moveFrom,moveTo));
            }
           
            createARedKing(playCount,MoveCheck.plusOne(moveFrom,moveTo));
        }  else{
           
           System.out.println("wrong move: next players turn");
    }
             
    }
        // method to create black king if the move to row element == 0
        private static void createARedKing(int playCount,String moveTo) throws IOException
            {
                String[] toXY = moveTo.split(",");
                if(Integer.parseInt(toXY[0])==0)
                {
                    Board.kings.addElement(moveTo);
                    Board.auditMove(playCount+"::white::king::created::"+ moveTo);
                }
    }




}
