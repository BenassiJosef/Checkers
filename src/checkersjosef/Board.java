/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author josefbenassi
 */
public class Board {
    
     // string for red colouring of  dot and king icon 
     public static final String ANSI_RED = "\u001B[31m";
     public static final String ANSI_RESET = "\u001B[0m";
     
     
   // Initialise vectors
    public  static Vector<String>  recordings          = new Vector<>(  );
    public  static Vector<String>  black               = new Vector<>(12);
    public  static Vector<String>  red                 = new Vector<>(12);
    public  static Vector<String>  invalid             = new Vector<>(32);
    public  static Vector<String>  vacant              = new Vector<>(64);
    public  static Vector<String>  occupied            = new Vector<>(24);
    public  static Vector<String>  recordedMoves       = new Vector<>(  );
    public  static Vector<String>  blackCaptures       = new Vector<>(12);
    public  static Vector<String>  redCaptures         = new Vector<>(12);
    public  static Vector<String>  kings               = new Vector<>(  );
   

    // method to add string values to black vector i.e [0,0 0,2 0,4...]
    private static void initialiseBlacks()
    {
       //black.clear();
       for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
               if( i==0  && MoveCheck.isEven(j))
                  black.addElement(i+","+j);
               else if(i==1 && MoveCheck.isOdd(j)) 
                  black.addElement(i+","+j);
               else if(i==2 && MoveCheck.isEven(j)) 
                  black.addElement(i+","+j);
            }
        } 
       
    }
    // method to add string values to red vector i.e [5,1 5,3 5,5...]
    private static void initialiseReds()
    {
      // red.clear();
       for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
               if( i==5  && MoveCheck.isOdd(j))
                  red.addElement(i+","+j);
               else if(i==6 && MoveCheck.isEven(j)) 
                  red.addElement(i+","+j);
               else if(i==7 && MoveCheck.isOdd(j)) 
                  red.addElement(i+","+j);
            }
        } 
       
    }
    
   // method to add string values to invalid vector i.e [0,1 0,3 0,5...]
    private static void initialiseInvalids()
    {
       //invalid.clear();
       for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
               if(MoveCheck.isEven(i) && MoveCheck.isOdd(j))
                  invalid.addElement(i+","+j);
               else if(MoveCheck.isOdd(i) && MoveCheck.isEven(j)) 
                  invalid.addElement(i+","+j);
            }
        } 
       
    } 
   
    // method to add string values to vacant vector i.e [3,1 3,3 3,5...]
    private static void initialiseVacant()
    {  
       //vacant.clear();
       for (int i = 3; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
               if(MoveCheck.isOdd(i) && MoveCheck.isOdd(j))
                  vacant.addElement(i+","+j);
               else if(MoveCheck.isEven(i) && MoveCheck.isEven(j)) 
                  vacant.addElement(i+","+j);
            }
        } 
       
    }
    
    // method to add string values from both black and red vector because their square state is occupied
    private static void initialiseOccupied()
    {
       //occupied.clear();
       for (String position : black)
       {
          occupied.addElement(position);
       }
       for (String position : red)
       {
          occupied.addElement(position);
       }   
    } 
    
    
      // 
   
    // prints board without any vector representation untill bulidcell can take in string elements. This happens when resest method is called
    public static void drawBoard()
    {
        Board.clearScreen();
        System.out.println("\\  0 | 1 | 2 | 3 | 4 | 5 | 6 | 7  /");
        System.out.println("  ---------------------------------");
        for (int i = 0; i < 8; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                row.append(buildCell(i,j));
            }
            if(i==2)
            {
                 System.out.println(i + "|" + row.toString() + " "+ i + "       Red player has captured " + blackCaptures.size()+ " Blacks");
                 System.out.println("  ---------------------------------"+ "        Black player has captured " + redCaptures.size()+ " Reds");
                 
            }
            else
            {
                System.out.println(i + "|" + row.toString() + " "+ i);
                System.out.println("  ---------------------------------");
            }
        }
        System.out.println("/  0 | 1 | 2 | 3 | 4 | 5 | 6 | 7  \\");
        
        
    } 
       // method to assign a character value to a paticular vector 1 true 0 false 
       // return varibale cell which prints a | after  vectors character symbol
       private static String buildCell(int x, int y) {
       
       String ans = ""; 
              
       ans += ((MoveCheck.isInVector(black,x+","+y)) ? "1" : "0");
       ans += ((MoveCheck.isInVector(red,x+","+y)) ? "1" : "0");
       ans += ((MoveCheck.isInVector(kings,x+","+y)) ? "1" : "0");
       ans += ((MoveCheck.isInVector(vacant,x+","+y)) ? "1" : "0");
       ans += ((MoveCheck.isInVector(invalid,x+","+y)) ? "1" : "0");
       
       String character="";
       
       switch(ans)
       {
           case "10100": character="\u265A"; break;// black king 
           case "00100": character=ANSI_RED+"\u2654"+ANSI_RESET; break;// white king 
           case "00001": character="\u2588"; break;
           case "00010": character=" "; break;
           case "01000": character=ANSI_RED+"\u25CF"+ANSI_RESET; break;//white cirlce
           case "10000": character="\u25CF"; break;//black cirlce
       }
       String cell = String.format(" %s |",character); 
        
       return cell;
    }
  

    // method called fills vectors with thier initial bored state defined in each method called within reSetGame
    public static void reSetGame()
    {
        //this.initialiseBoard();
        Board.initialiseBlacks();
        Board.initialiseReds();
        Board.initialiseInvalids();
        Board.initialiseVacant();
        Board.initialiseOccupied();
       

        recordedMoves.clear();
        blackCaptures.clear();
        redCaptures.clear();
        kings.clear();
    }
    
    public static void clearScreen()
    {
        System.out.println("");
        System.out.print("\033[H\033[2J"); // clears the console screen
      
    }
  
    //audit move takes a string for its parameter and adds this to the recorded moves vector
    public static void auditMove(String auditPoint) throws IOException
    {
      recordedMoves.addElement(auditPoint);
      if(Record.isRecording)//true
          PlayGame.fos.write((auditPoint+"\n").getBytes());
      
    }
    
    
}