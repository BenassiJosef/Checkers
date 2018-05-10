/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author josefbenassi
 */
public class PlayGame {
    
     public static int playCount = 0; // sets playcount
    
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));// sets to read an input
     public static FileOutputStream fos = null;// sets variable to write data to file. varaible modified in Board class
     
     static boolean inSession = false;// sets boolean false
     static boolean isPlaying = false;// sets boolean false
   
     
     String[] player = new String[2];// player array 2 index 0 and 1
     
     
     
     
     public void play() throws IOException
    {
      
      String prompt;  // set string varaible prompt
      inSession = true;  // boolean now true
      
      while(inSession)
      {
          
        Record.checkForRecordings();// mehtod in Recod class to check recorded games
        if(!Board.recordings.isEmpty())
        {
              Board.clearScreen();
              System.out.print("Do You Want To Replay A Saved Session [y/n]? : ");
              prompt = br.readLine();
              if(prompt.equalsIgnoreCase("y"))
                  Record.listRecordedGames(); // show the users game and take input
              else
                  playGame();
        }
        else
            playGame();
      }  
    }
    
        private void playGame() throws IOException
    {
            String prompt;
            Board.clearScreen();// clear screen 
            System.out.print("Do You Want To Record Session [y/n]? : ");
            prompt = br.readLine();
            if(prompt.equalsIgnoreCase("y"))
                 Record.recordGame();
            
            Board.clearScreen();            
            System.out.println("Press 1 human vs AI ");
            System.out.println("Press 2 for human vs human");
            System.out.println("Press 3 for AI vs AI");
            System.out.println("Press e to exit or r to restart");
            
            System.out.print("Select Option : ");
            prompt = br.readLine();
            switch(prompt){
                case "e": case "E":{Record.exitGame(); break;}
                case "r": case "R":{return;}
                case "1": {capturePlayersNameAndRobot();break;}
                case "2": { capturePlayersNames(); break;}
                case "3": { captureAINames(); break;}
                default: return;
            }
            
            Board.reSetGame();
            //drawBoard();
            Board.clearScreen();
           
            isPlaying = true;
              int turn = 0;
              while(isPlaying)
              {
                if(Board.redCaptures.size()==12)
                {
                    finishAndCongratualte("Black");
                    isPlaying=false;
                    break;
                }
                
                if(Board.blackCaptures.size()==12)
                {
                    finishAndCongratualte("Red");
                    Record.isPlaying=false;
                    break;
                }
                            
                Board.clearScreen();  
                Board.drawBoard();
                System.out.println(" ");


                String color = (turn==0) ? "Black":"Red";
                
                //boolean state=(turn==0) ? whatCanBlackPlay():whatCanWhitePlay();
                String[] move = new String[2];
                if(turn==1 && (player[1].contains("-LekuiXojskjskkws")))//check for bot
                {     move=AI.whatCanUpBoardPlay(Board.red,Board.black);
                      if(move[1]==null)
                      { 
                          finishAndCongratualte(color);
                          isPlaying=false;
                          break;
                      }
                      System.out.print(String.format("%s,  %s move will be: [ %s, %s ] <press enter to continue>",player[turn],color,move[0],move[1]));
                      br.readLine();            
                }
                else if(turn==0&& (player[0].contains("-LekuiXojskjskkwsblack")))//check for bot
                {     move=AI.whatCanDownBoardPlay(Board.black,Board.red);
                      if(move[0]==null)
                      { 
                          finishAndCongratualte(color);
                          isPlaying=false;
                          break;
                      }
                      System.out.print(String.format("%s,  %s move will be: [ %s, %s ] <press enter to continue>",player[turn],color,move[0],move[1]));
                      br.readLine();            
                }
                else
                {
                    if(turn==1)
                        AI.whatCanUpBoardPlay(Board.red,Board.black);
                    else
                        AI.whatCanDownBoardPlay(Board.black,Board.red);
                    
                    System.out.print(String.format("%s, enter your %s move: [rowFrom,colFrom rowTo,colTo] ",player[turn],color));
                    move = (br.readLine()).split(" ");
                }
                if(move.length==2)
                {
                  if(turn==0)
                      Black.moveBlack(playCount,move[0], move[1]);
                  else
                      Red.moveRed(playCount,move[0], move[1]); 
                  

                  Board.drawBoard();
                }

               if(!player[1].contains("-LekuiXojskjskkws")) 
               {
                    System.out.print(String.format("%s, do you want to undo your %s move [ y/n ]? : ",player[turn],color));
                    String ans = br.readLine();
                    if(ans.equalsIgnoreCase("y"))
                    {
                        MoveCheck.undoLastMove();    
                        Board.drawBoard();

                       System.out.print(String.format("%s, do you want to redo the undo your %s move [ y/n ]? : ",player[turn],color));
                       ans = br.readLine();
                       if(ans.equalsIgnoreCase("y"))
                       {
                           MoveCheck.redoLastUndo();    
                           Board.drawBoard();
                       }
                    }
               }
                  if(!player[0].contains("-LekuiXojskjskkwsblack")) 
               {
                    System.out.print(String.format("%s, do you want to undo your %s move [ y/n ]? : ",player[turn],color));
                    String ans = br.readLine();
                    if(ans.equalsIgnoreCase("y"))
                    {
                        MoveCheck.undoLastMove();    
                        Board.drawBoard();

                       System.out.print(String.format("%s, do you want to redo the undo your %s move [ y/n ]? : ",player[turn],color));
                       ans = br.readLine();
                       if(ans.equalsIgnoreCase("y"))
                       {
                           MoveCheck.redoLastUndo();    
                           Board.drawBoard();
                       }
                    }
               }
               

               playCount++;
               if(turn ==1) turn=0;
                  else turn=1;
              }      
    }
     private void capturePlayersNames() throws IOException
    {
            System.out.print(String.format("Player %d enter your NAME - [you will be Black] : ",1));
            player[0] = br.readLine();
            System.out.print(String.format("Player %d enter your NAME - [you will be Red] : ",2));
            player[1] = br.readLine();
    }
    
     private void capturePlayersNameAndRobot() throws IOException
    {
            System.out.print(String.format("Player %d enter your NAME - [you will be BLACK] : ",1));            
            player[0] = br.readLine();
            System.out.print(String.format("Player %d will be Botty - [it will be Red] <press enter to continue>" ,2));
            player[1] = "botty-LekuiXojskjskkws";
            br.readLine();
    }
    
     private void captureAINames() throws IOException {
      
            System.out.print(String.format("Player %d will be Botty1 - [it will be Black]<press enter to continue>",1));            
            player[0] = "botty-LekuiXojskjskkwsblack";
            br.readLine();
            System.out.print(String.format("Player %d will be Botty2 - [it will be Red] <press enter to continue>" ,2));
            player[1] = "botty-LekuiXojskjskkws";
            br.readLine();
        
    
    }
     
     private void finishAndCongratualte(String winner) throws IOException {
        Board.clearScreen();
        System.out.println("THE WINNER IS " + winner);
        System.out.println("THE WINNIG BOARD ");
        Board.drawBoard();
        
        System.out.print("Exit e or return to Menu m [ e/m ]");
        String prompt = br.readLine();
        boolean needAnswer = true;
        while(needAnswer){
            switch (prompt){
                case "m": case "M": return;
                case "e": case "E": needAnswer=false; Record.exitGame(); break;
            }

        }
        
    }

   
    
    
     
     
     



}
