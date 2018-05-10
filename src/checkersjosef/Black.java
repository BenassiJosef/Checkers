/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import checkersjosef.MoveCheck;
import java.io.IOException;

/**
 *
 * @author josefbenassi
 */
public class Black {
    
    //method for moving black checkers, adds and removes from vectors based upon the movefrom and moveto
    
    public static void moveBlack(int playCount,String moveFrom, String moveTo) throws IOException
    {
        boolean isKing = Board.kings.contains(moveFrom);
        
        if(MoveCheck.isInVector(Board.black,moveFrom) && MoveCheck.isInVector(Board.vacant,moveTo) && !MoveCheck.isInVector(Board.invalid,moveTo)&& MoveCheck.isBlackDirectionOk(moveFrom,moveTo))
        {          
           
                Board.black.removeElement(moveFrom);
                Board.auditMove(playCount+"::black::remove::"+ moveFrom);
                
                if(isKing)
                {
                    Board.kings.removeElement(moveFrom);
                    Board.auditMove(playCount+"::kings::remove"+ moveFrom);
                }
                Board.occupied.removeElement(moveFrom);
                Board.auditMove(playCount+"::occupied::remove::"+ moveFrom);
                
                Board.vacant.removeElement(moveTo);
                Board.auditMove(playCount+"::vacant::remove::"+ moveTo);
                
                Board.vacant.addElement(moveFrom);
                Board.auditMove(playCount+"::vacant::add::"+ moveFrom);
                
                Board.occupied.addElement(moveTo);
                Board.auditMove(playCount+"::occupied::add::"+ moveTo);
                
                Board.black.addElement(moveTo);
                Board.auditMove(playCount+"::black::add::"+ moveTo);
                
                if(isKing)
                {
                    Board.kings.addElement(moveTo);
                    Board.auditMove(playCount+"::kings::add::"+ moveTo);
                }
                
                createABlackKing(playCount,moveTo);
                return;
            }
         
    //method for moving black checkers, adds and removes from vectors based upon the movefrom and moveto for black jump
        if(MoveCheck.isInVector(Board.black,moveFrom) && MoveCheck.isInVector(Board.occupied,moveTo) && MoveCheck.isInVector(Board.red,moveTo))
        {   
        
            if(MoveCheck.isInVector(Board.vacant,MoveCheck.plusOne(moveFrom,moveTo))) 
            {
                
                if(!MoveCheck.isInVector(Board.invalid,MoveCheck.plusOne(moveFrom,moveTo)))
                {
                    
                    if(MoveCheck.isBlackDirectionOk(moveFrom,moveTo))
                    {
                        
                        Board.black.removeElement(moveFrom);
                        Board.auditMove(playCount+"::black::remove::"+ moveFrom);
                        
                        if(isKing)
                        {
                            Board.kings.removeElement(moveFrom);
                            Board.auditMove(playCount+"::kings::remove"+ moveFrom);
                        }
                        
                        Board.occupied.removeElement(moveTo);
                        Board.auditMove(playCount+"::occupied::remove::"+ moveTo);
                        
                        Board.red.removeElement(moveTo);
                        Board.auditMove(playCount+"::white::remove::"+ moveTo);
                        
                        Board.redCaptures.addElement(moveTo);
                        Board.auditMove(playCount+"::whiteCaptures::add::"+ moveTo);
                        
                        Board.vacant.addElement(moveTo);
                        Board.auditMove(playCount+"::vacant::add::"+ moveTo);
                        
                        Board.vacant.addElement(moveFrom);
                        Board.auditMove(playCount+"::vacant::add::"+ moveFrom);
                        
                        Board.occupied.addElement(MoveCheck.plusOne(moveFrom,moveTo));
                        Board.auditMove(playCount+"::occupied::add::"+ MoveCheck.plusOne(moveFrom,moveTo));
                        
                        Board.black.addElement(MoveCheck.plusOne(moveFrom,moveTo));
                        Board.auditMove(playCount+"::black::add::"+ MoveCheck.plusOne(moveFrom,moveTo));
                        
                        Board.vacant.removeElement(MoveCheck.plusOne(moveFrom,moveTo));
                        Board.auditMove(playCount+"::vacant::remove::"+ MoveCheck.plusOne(moveFrom,moveTo));
                        
                        if(isKing)
                        {
                            Board.kings.addElement(MoveCheck.plusOne(moveFrom,moveTo));
                            Board.auditMove(playCount+"::kings::add::"+ MoveCheck.plusOne(moveFrom,moveTo));
                        }
                        createABlackKing(playCount,MoveCheck.plusOne(moveFrom,moveTo));
                        
                    }
                    
                }
                
            }
         
        }  
             
    }   
        // method to create black king if the move to row element == 7
        private static void createABlackKing(int playCount, String moveTo) throws IOException
    {
        String[] toXY = moveTo.split(",");
        if(Integer.parseInt(toXY[0])==7)
        {
            Board.kings.addElement(moveTo);
            Board.auditMove(playCount+"::black::king::created::"+ moveTo);
        }
    }
        
        
        
        
    }
    
    
    
    

