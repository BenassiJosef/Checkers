/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import java.util.Vector;

/**
 *
 * @author josefbenassi
 */
public class AI {
    

     private static String calcUpBoardAllowablePlays(String currPosition, Vector<String> secondary)// takes a s
    {
       String[] frmXY = currPosition.split(",");
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
       
       
       //now have co ords r = right l= left r move right l move left 
       
       int rx1=x1;
       int ry1=y1;
       
       int lx1=x1;
       int ly1=y1;
       
       
       boolean isFree=true;
       StringBuilder moves = new StringBuilder();
       while(isFree)
       {   
           rx1--;// moveing up increase x decrease y move down 
           ry1++;
           if(Board.vacant.contains(rx1+","+ry1)){ // moves is poosivle moves because its in vaccant 
               moves.append(rx1).append(",").append(ry1).append(":");
               moves.append(calcUpBoardAllowablePlaysLeftUp(rx1+","+ry1));// call the funtion agian nested  calls itsefl unitll it cant move anymore
           }
           else if(secondary.contains(rx1+","+ry1) && Board.vacant.contains((rx1-1)+","+(ry1+1))) //tsecond poart of this if statemnet looks at position ahead 
           {
               if(isANextMove(x1,y1,rx1,ry1)) 
               {    
                    moves.append("[").append(rx1).append(",").append(ry1).append("]").append(":"); 
                    moves.append(calcUpBoardAllowablePlays((rx1-1)+","+(ry1+1),secondary)); // string braces describes this as a jumo moves
                    
               }
               else
                moves.append(rx1+","+ry1).append(":");
               moves.append(calcUpBoardAllowablePlaysLeftUp((rx1-1)+","+(ry1+1)));//left
           }
           else
               isFree=false;
       }
       
       
       isFree=true;
       while(isFree)
       {   
           lx1--;         //move up 
           ly1--;
           if(Board.vacant.contains(lx1+","+ly1)){
               moves.append(lx1).append(",").append(ly1).append(":");
               moves.append(calcUpBoardAllowablePlaysRightUp(lx1+","+ly1));//left
           }
            else if(secondary.contains(lx1+","+ly1) && Board.vacant.contains((lx1-1)+","+(ly1-1))) //test if nezt is a black and can be jumped
           {
               if(isANextMove(x1,y1,lx1,ly1)) 
               {
                    moves.append("["+lx1+","+ly1+"]").append(":");//wrap the jump space
                    moves.append(calcUpBoardAllowablePlays((lx1-1)+","+(ly1-1),secondary));
               }
               else
                   moves.append(lx1+","+ly1).append(":");//wrap the jump space
               moves.append(calcUpBoardAllowablePlaysRightUp((lx1-1)+","+(ly1-1))); //check for moves after jump
           }
           else
               isFree=false;
       }
       
       
       //return possibleMoves;
       return moves.toString();
    }
 
     private static String calcUpBoardAllowablePlaysLeftUp(String currPosition)//whats can reds play to the left
    {
       String[] frmXY = currPosition.split(",");
       StringBuilder moves = new StringBuilder();
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
       
       boolean isFree=true;
       while(isFree)
       {
           x1--;
           y1--;
           if(Board.vacant.contains(x1+","+y1))
                 moves.append(x1+","+y1).append(":");
           else
               isFree=false;
       }
       return moves.toString();
    }   
    
    private static String calcUpBoardAllowablePlaysRightUp(String currPosition)//what can reds play to the right
    {
       StringBuilder moves = new StringBuilder();
       String[] frmXY = currPosition.split(",");
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
       
       boolean isFree=true;
       while(isFree)
       {
           x1--;
           y1++;
           if(Board.vacant.contains(x1+","+y1))
               moves.append(x1+","+y1).append(":");
           else
               isFree=false;
       }
       return moves.toString();
    }
  
    public static String[] whatCanUpBoardPlay(Vector<String> primary, Vector<String> secondary)//whats can reds play method // altogether
    {
        //System.out.println("Calculating Up Board Plays");
        String moveFrom="";
        String moveTo = "";
        for(String primaryPos : primary)
        {
            //moves are now string chose a move with the longest ath either right or left , but choose jump move before left or right 
            
            
             // always take a jump
            // always take the fist poistion out the longest path ie the length of strings for right and length for left
            
            String val = calcUpBoardAllowablePlays(primaryPos,secondary);
            //System.out.println(primaryPos + " has " + val + " moves.");
            if(val.contains("["))
              val+="00000000000000000000000000000000000";// // padding to ensure jump moves are a priority
            if(val.length()>moveTo.length())
            {
                moveFrom = primaryPos; // make move based on srting lenth 
                moveTo=val;
            }
            
            boolean isKing = Board.kings.contains(primaryPos);
            if(isKing) // then check for upboard moves as king can move up and down
            {
                val = calcDownBoardAllowablePlays(primaryPos,secondary);// king moves
                            if(val.contains("["))
                val+="00000000000000000000000000000000000";// extend length so can not be overridden
            
                if(val.length()>moveTo.length())
                {
                    moveFrom = primaryPos;
                    moveTo=val;
                }
            
            }            
            
        }
        
        if(moveTo.contains("["))
            moveTo = moveTo.substring(moveTo.indexOf("[")+1,moveTo.indexOf("]"));
        else if(moveTo.contains(":"))
            moveTo = moveTo.substring(0, moveTo.indexOf(":"));
        else
            moveTo = null;
        
        //System.out.println("The white play is "+moveFrom+" "+moveTo);
        
        return new String[]{moveFrom,moveTo};// returns a move to move from 
    }
    
    
    private static String calcDownBoardAllowablePlays(String currPosition, Vector<String> secondary)//down
    {
       StringBuilder moves = new StringBuilder();
       String[] frmXY = currPosition.split(",");
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
      
       int rx1=x1;
       int ry1=y1;
       
       int lx1=x1;
       int ly1=y1;
       
       //start moving right
       boolean isFree=true;
       while(isFree)
       {   
           rx1++;
           ry1++;
           if(Board.vacant.contains(rx1+","+ry1)){ //test if next move is vacant
               moves.append(rx1+","+ry1).append(":");
               moves.append(calcDownBoardAllowablePlaysLeftUp(rx1+","+ry1));
           }
           else if(secondary.contains(rx1+","+ry1) && Board.vacant.contains((rx1+1)+","+(ry1+1))) //test if nezt is a ehite and can be jumped
           {
               if(isANextMove(x1,y1,rx1,ry1))
               {
                 moves.append("["+rx1+","+ry1+"]").append(":"); // wrap up the jump case 
                 moves.append(calcDownBoardAllowablePlays((rx1+1)+","+(ry1+1),secondary));
               }
               else    
                 moves.append(rx1+","+ry1).append(":"); // wrap up the jump case
        
               moves.append(calcDownBoardAllowablePlaysLeftUp((rx1+1)+","+(ry1+1))); //test the place+1 for next step
           }
           else
               isFree=false;
       }
       
        //start moving left
       isFree=true;
       while(isFree)
       {   
           lx1++;
           ly1--;
           if(Board.vacant.contains(lx1+","+ly1)){
               moves.append(lx1+","+ly1).append(":");
               moves.append(calcDownBoardAllowablePlaysRightUp(lx1+","+ly1));
           }
            else if(secondary.contains(lx1+","+ly1) && Board.vacant.contains((lx1+1)+","+(ly1-1))) //test if nezt is a ehite and can be jumped
           {
              if(isANextMove(x1,y1,lx1,ly1))
              {
                moves.append("["+lx1+","+ly1+"]").append(":");
                moves.append(calcDownBoardAllowablePlays((lx1+1)+","+(ly1-1),secondary));
              }
              else
                moves.append(lx1+","+ly1).append(":");
              
               moves.append(calcDownBoardAllowablePlaysLeftUp((lx1+1)+","+(ly1-1)));
           }
           else
               isFree=false;
       }
       
       //return possibleMoves;
       return moves.toString();
    }
    
    private static String calcDownBoardAllowablePlaysLeftUp(String currPosition)//down
    {
       StringBuilder moves = new StringBuilder();
       String[] frmXY = currPosition.split(",");
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
       
       boolean isFree=true;
       while(isFree)
       {
           x1++;
           y1--;
           if(Board.vacant.contains(x1+","+y1))
                moves.append(x1+","+y1).append(":");
           else
               isFree=false;
       }
       return moves.toString();
    }
    
     private static String calcDownBoardAllowablePlaysRightUp(String currPosition)//down
    {
       StringBuilder moves = new StringBuilder();
       String[] frmXY = currPosition.split(",");
       int x1 = Integer.parseInt(frmXY[0]);
       int y1 = Integer.parseInt(frmXY[1]);
       
       boolean isFree=true;
       while(isFree)
       {
           x1++;
           y1++;
           if(Board.vacant.contains(x1+","+y1))
                moves.append(x1+","+y1).append(":");
           else
               isFree=false;
       }
       return moves.toString();
    }   
    
    
    public static  String[] whatCanDownBoardPlay(Vector<String> primary, Vector<String> secondary)//what can black play altogether
    {
        //System.out.println("Calculating DownBoard Plays");
        String moveFrom="";
        String moveTo = "";
        String[][] results=new String[12][2];
        for(String primaryPos : primary)
        {
            //int val = calcBlackAllowablePlays(blackPos);
            
            String val = calcDownBoardAllowablePlays(primaryPos,secondary);
           // System.out.println(primaryPos + " has " + val + " moves.");
            if(val.contains("["))
              val+="00000000000000000000000000000000000";// extend length so can not be overridden
            
            if(val.length()>moveTo.length())
            {
                moveFrom = primaryPos;
                moveTo=val;
            }
            
            boolean isKing = Board.kings.contains(primaryPos);
            if(isKing) // then check for upboard moves as king can move up and down
            {
                val = calcUpBoardAllowablePlays(primaryPos,secondary);
                            if(val.contains("["))
                val+="00000000000000000000000000000000000";// extend length so can not be overridden
            
                if(val.length()>moveTo.length())
                {
                    moveFrom = primaryPos;
                    moveTo=val;
                }
            
            }
            
        }

        if(moveTo.contains("[")){
            moveTo = moveTo.substring(moveTo.indexOf("[")+1,moveTo.indexOf("]"));
            /*
            int pos=0;
            while(moveTo.contains("["));
            {  
              results[pos][0] = moveTo.substring(moveTo.indexOf("[")+1,moveTo.indexOf("]"));
              moveTo = moveTo.replaceAll(results[pos][0], "");
            }
            */
        }
        else if(moveTo.contains(":"))
            moveTo = moveTo.substring(0, moveTo.indexOf(":"));
        else
            moveTo = null;
        
       // System.out.println("The black play is "+moveFrom+" "+moveTo);
        
        return new String[]{moveFrom,moveTo};
    }
    
    
    // method returns a true or false statement tkes 4 integers in its parameters
     private static boolean isANextMove(int x1,int y1,int x2,int y2)// condition method used in "what can be played", methods
     { // making suer move is valid 
        boolean isANextMove=false;
        int tx, ty = 0; 
        
        if(x2==0 && x1==0)
             tx = 0;
        else if(x2==0 && x1!=0)
            tx =((x1*x1)/x1); // getting gradiet valid
        else if(x1==0 && x2!=0)
             tx =0-((x2*x2)/x2); // get rid of negative values minus for gradient
        else    
            tx = ((x1*x1)/x1)-((x2*x2)/x2);
        
        if(y2==0 && y1==0)
             ty = 0;
        else if(y2==0 && y1!=0)
            ty =((y1*y1)/y1);
        else if(y1==0 && y2!=0)
             ty =0-((y2*y2)/y2); 
        else if(y1!=0 && y2!=0)   
            ty = ((y1*y1)/y1)-((y2*y2)/y2);
        
        if( (tx*tx)==1 && (ty*ty)==1)
            isANextMove=true;
        
        return isANextMove;
    }





}
