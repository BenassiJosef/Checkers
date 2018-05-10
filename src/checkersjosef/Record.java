/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import static checkersjosef.PlayGame.fos;
import static checkersjosef.PlayGame.playCount;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.exit;

/**
 *
 * @author josefbenassi
 */
public class Record {
    
   //initialise bbuffer reader reads user input
   public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   
   static boolean isRecording = false;
   static boolean isPlaying = false;
   
   
   // method sets variable isRecording from the play game class to true and calls recording method
   
   public static void recordGame() throws IOException{ // called in PlayGame class
         isRecording = true;
         recording();
    
    } // called in playgame
   
    // method to set a new file for the recording of game  
   
   private static void recording() throws IOException{
        
        boolean entryGood = false;
        String recordedName = null;
        File file = null;
        while(!entryGood)
        {
            System.out.print("Provide Name for Recording : ");
            recordedName = br.readLine();
            if(recordedName!=null && recordedName.length()>0)
            {
                file = new File(recordedName);
                if(!file.exists())
                    entryGood = true;
                else
                    System.out.println("File Already Exists Please Try Again. ");
            }
            else
                System.out.println("Name Invalid Please Retry. ");
        }
        file = new File(recordedName+".rec");
        fos = new FileOutputStream(file);
        isRecording=true;
    }// called in method recordGame Methd
   // method checks if there are any recording  
   
   static void checkForRecordings()//calledin PlayGame class
    {
        Board.recordings.clear(); // clears recording vector initialised in Board class
        File folder = new File("./");// creates folder path
        File[] listOfFiles = folder.listFiles(); // file array holds files in folder

        
         for (File listOfFile : listOfFiles) { // loop iterates over array and if statement sets conditions
             if (listOfFile.isFile() && listOfFile.getName().endsWith(".rec")) 
                 Board.recordings.addElement(listOfFile.getName()); // add the file name to recording vector
         } 
    }
   
   // called in PlayGame
   public static void  listRecordedGames() throws IOException {
         
         boolean answerNeeded = true;
         while(answerNeeded)
         {
            String prompt=""; 
            Board.clearScreen();
            for(String record : Board.recordings) // searchers recording vector and outputs files names
                System.out.println(record);
            System.out.println(""); 
            try{ 
                
                System.out.print("Type the name of the recording you would like to replay question : "); //prompt user
                prompt = br.readLine();
                if(prompt.equalsIgnoreCase("again"))
                    return;
                loadRecordedGame(prompt);
                answerNeeded=false;   // this try loads the the file names repetedly if again is typed
            }
            catch(FileNotFoundException e)
            {
                answerNeeded=true;
                //clearScreen();
                System.out.println("The file \""+prompt+"\" does not exists.");
                System.out.println("Type in one of the listed recordings only.");
                System.out.println("Or press m for Menu.");
                
            } 
         }
        
    } // called in Plapy
    
   // method takes a string in its paramaters ie a file name and actsupon it.
   public static void  loadRecordedGame(String gameName) throws FileNotFoundException, IOException {
        Board.reSetGame(); //reset the board
        InputStream is = new FileInputStream(gameName);  // the input stream now takes form of file 
        BufferedReader buf = new BufferedReader(new InputStreamReader(is)); //read the inout stream 
        String line = buf.readLine();  
        while(line != null)
        {
           Board.recordedMoves.addElement(line); // while there are lineds add the lines to recorded moves vector
           line = buf.readLine(); 
        } 
        System.out.print(gameName + " has been loaded do you want to run it [y/n]? "); // tell the user the vector has be filled
        String prompt = br.readLine();
        if(prompt.equalsIgnoreCase("y"))
              playRecordedGame();// play the game here
        
        boolean needAnswer = true;
        while(needAnswer)
        {
            System.out.print("Go Back To Menu or Exit [m/e]? ");
            prompt = br.readLine();

            if(prompt.equalsIgnoreCase("e"))
                exitGame();    
            else if(prompt.equalsIgnoreCase("m"))
                needAnswer=false;
        }
    }
   
   // how to play a recroded game
   public static void  playRecordedGame() throws IOException{
        
        int plays = calculateNumberOfPlaysRecorded();
        for(int play = 0; play<plays; play++) // play will be less than plays 
        {
            PlayGame.playCount=play;
            Board.drawBoard();// draw the board on screen 
            MoveCheck.redoLastUndo(); // redo the last undo 
            System.out.print("Play next move ["+play+"] or go back to Menu or Exit [p/m/e]? "); // propmt user id they want to continue game 
            String prompt = br.readLine();
            boolean needAnswer = true;
            while(needAnswer){
                switch (prompt){
                    case "p": case "P": needAnswer=false; break; //false so carry a keep getting plays
                    case "m": case "M": return;
                    case "e": case "E": needAnswer=false; exitGame(); break;
                }
           
            }
            
        }
        Board.drawBoard();// draw the loaded game board
        System.out.println("All "+plays+" plays have been played from this recording."); //come to end of plays
        System.out.println("Press <enter> to continue");
        br.readLine();
    }
    
   public static int  calculateNumberOfPlaysRecorded()
    {
        int plays = 0;
        int play =-1;
        for(String entry : Board.recordedMoves) // itterate through recorded moves
        {
            int testPlay;
            testPlay = Integer.parseInt(entry.substring(0, entry.indexOf("::"))); // test plays finding plays making them ints
            if(testPlay!=play) //if there are plays, plays is counting number of plays
                plays++;
            
            play=testPlay; // set play to a number value of how many times testplays was iterated through 
        }
        
        return plays; // return this number
    }
    
    /**
     *
     */
   public static void  exitGame() //exit the gamae method
    {
        System.out.print("Exiting.... \n");
        exit(0);
    }





}
