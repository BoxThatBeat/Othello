/* Aaron Buitenwerf
 * First game Assignment
 * Othhello_SinglePlayer
 */

import java.util.Random;
class Othello_Ai
{
  public static char turn, turnOpp;
  public static int input1, input2;
  public static char[][] board = new char[8][8];
  
  public static void main (String [] args) //calls all needed methods
  {
    
    for (int k = 0; k < 8; k++) //This set of code sets the whole board to spaces
    { 
      for (int h = 0; h < 8; h++)
      {
        board[k][h] = ' ';
      }
    }  
    board[3][3] = 'x'; //sets the default piece positions
    board[4][4] = 'x';
    board[3][4] = 'o';
    board[4][3] = 'o';
    
    int player1PTs = 0, player2PTs = 0; //for counting to see who won the game
    int endCondition = 0; 
    String in;
    int cpu = 0;
    
    System.out.println("Do you want to see how the cpu scores its moves?");
    System.out.println("if yes type y");
    in = In.getString(); 
    in = in.toUpperCase();
    if (in.equals ("Y")) //activates the cpu mode
    {
      cpu = 1;
    }
    else {}
    
    System.out.println("Do you know how to play Othello?");
    System.out.println("type n to display instructions");
    in = In.getString(); //gets input of user, if they type anything other than N the game will start without instructions
    in = in.toUpperCase();
    if (in.equals ("N"))
    {
      rules(); //calls the rules method which shows the rules of the game
    }
    else{}
    
    System.out.println("Let's Start"); //game starts
    
    System.out.println("type in 8 then 8 again to skip your turn");
    System.out.println("type in the row then the column in two seperate inputs"); //first Y then X axis
    
    Random randomGenerator = new Random(); // random number between 0 to 1
    int rand = randomGenerator.nextInt(2);
    if (rand == 0) //if the random number is equal to 0 say who is who
    {
      System.out.println("Player 1 you are the 'x's");
      System.out.println("Ai is the 'o's");
    }
    else if (rand == 1)
    {
      System.out.println("Player 1 you are the 'o's");
      System.out.println("Ai is the 'x's");
    }
    do //this loop sets whos turn it is by setting turnopp to the players piece
    {
      endCondition = 0;
      
      if (rand == 0)
      {
      System.out.println("Player 1 it is your turn");
      turn = 'o';//these variables are used in the method "play" to show whos turn it is to the game logic
      turnOpp = 'x';
      }
      else if (rand == 1)
      {
      System.out.println("Player 1 it is your turn");
      turn = 'x';
      turnOpp = 'o';
      }
      play(); //this is the gamelogic for when both player1 and player2 play their turn
      
      if (rand == 0)
      {
      turn = 'x';
      turnOpp = 'o';
      }
      else if (rand == 1)
      {
      System.out.println("Ai is the 'x's");
      turn = 'o';
      turnOpp = 'x';
      }
      playAi(cpu); //calls the Ai method which will chouse the best move possible and play it
      
      for (int k = 0; k < 8; k++) //This loop checks the entire board
      {
        for (int h = 0; h < 8; h++)
        {
          if (board[k][h] != ' ')
          {
           endCondition ++; 
          }
        }
      }
      System.out.println("Pieces on the board: " + endCondition);
    }
    while (endCondition != 64); //the loop stops repeating when each player has played 32 pieces

    System.out.println("Game Over");
    for (int k = 0; k < 8; k++) //This loop checks the entire board
    { 
      for (int h = 0; h < 8; h++)
      {
        if (board[k][h] == 'o')
        {
          player1PTs++; //this adds to the count of how many points player1 has (o's)
        }
        if (board[k][h] == 'x')
        {
          player2PTs++; //this adds to the count of how many points player2 has (x's)
        }
      }
    }
    
    if (player1PTs == player2PTs) //this determines who wins or if there was a tie
    {
      System.out.println("It was a tie!");
    }
    else if (player1PTs > player2PTs)
    {
      System.out.println("Player1 won!");
    }
    else if (player2PTs > player1PTs)
    {
      System.out.println("Defeat");
      System.out.println("Ai won");
    }
    System.out.println("GG"); //good game
  }
  
  public static void play() //this is the game logic and error checking
  {
    int tryAgain = 0; //Try again is a var that goes up by one evertime there is a invalid gamelogic or if there is a big error it will go straigt to 8
      do              //the turn will go again if the count is equal to 8 (exception 15 for specific error note)
      {
        if (tryAgain == 8) 
        {
          System.out.println("Not a valid move"); //an if statement that has the same conditions as the while statement at the end
          System.out.println("Try again");        // this says a note that the player needs to try again
          System.out.println("8 , 8 to skip turn"); //reminder that 8 then 8 is to skip turn (in case there is no possible move)
        }
        tryAgain = 0; //sets tryAgain back to 0
        printBoard(); //prints the board even if nothing has changed
        input1 = In.getInt(); //gets the two inputs of the player
        input2 = In.getInt();
        if (input1 > 8 || input2 > 8) //checking for legitimacy of input
        {
          System.out.println("Please input only 0-8"); //only allows 0-8 as an integer
          System.out.println("8 , 8 to skip turn");
          System.out.println("Try again");
          tryAgain = tryAgain + 15;
        }
        else if (input1 < 0 || input2 < 0)
        {
          System.out.println("Please input only 0-8");
          System.out.println("8 , 8 to skip turn");
          System.out.println("Try again");
          tryAgain = tryAgain + 15;
        }
        else
        {
          if (input1 == 8 && input2 == 8) //if both inputs are equal to 8
          {
            System.out.println("Skipping your turn");
            tryAgain--; //this makes it impossible to restart because tryAgain will never equal 8
          }
          else
          {
            if (check() == false) //goes to a method that checks if the player is trying to place a piece on another piece
            {
              tryAgain = tryAgain + 8; //goes straight to 8 and skips the game logic
            }
            else
            {
              try //this try is to eliminate the error that occurs when the game logic trys to check a edge piece but goes outside the array
              {
                //For downwards change
                if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turnOpp) //if there is an opponents piece inbetween the player's piece in the downwards direction
                {
                  board[input1][input2] = turnOpp; //set a piece on the position identified by the player
                  board[input1 + 1][input2] = turnOpp; //set the piece that is inbtween
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turn && board[input1 + 5][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                  board[input1 + 4][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turn && board[input1 + 5][input2] == turn && board[input1 + 6][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                  board[input1 + 4][input2] = turnOpp;
                  board[input1 + 5][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turn && board[input1 + 5][input2] == turn && board[input1 + 6][input2] == turn && board[input1 + 7][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                  board[input1 + 4][input2] = turnOpp;
                  board[input1 + 5][input2] = turnOpp;
                  board[input1 + 6][input2] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e) //for the exception of playing in corners or edges of board
              {
                tryAgain++;
              }
              
              try
              {
                //For upwards change
                if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turn && board[input1 - 5][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                  board[input1 - 4][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turn && board[input1 - 5][input2] == turn && board[input1 - 6][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                  board[input1 - 4][input2] = turnOpp;
                  board[input1 - 5][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turn && board[input1 - 5][input2] == turn && board[input1 - 6][input2] == turn && board[input1 - 7][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                  board[input1 - 4][input2] = turnOpp;
                  board[input1 - 5][input2] = turnOpp;
                  board[input1 - 6][input2] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
              try 
              {
                //for left change
                if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turn && board[input1][input2 - 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                  board[input1][input2 - 4] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turn && board[input1][input2 - 5] == turn && board[input1][input2 - 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                  board[input1][input2 - 4] = turnOpp;
                  board[input1][input2 - 5] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turn && board[input1][input2 - 5] == turn && board[input1][input2 - 6] == turn && board[input1][input2 - 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                  board[input1][input2 - 4] = turnOpp;
                  board[input1][input2 - 5] = turnOpp;
                  board[input1][input2 - 6] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
              
              try 
              {
                //For right change
                if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turn && board[input1][input2 + 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                  board[input1][input2 + 4] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turn && board[input1][input2 + 5] == turn && board[input1][input2 + 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                  board[input1][input2 + 4] = turnOpp;
                  board[input1][input2 + 5] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turn && board[input1][input2 + 5] == turn && board[input1][input2 + 6] == turn && board[input1][input2 + 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                  board[input1][input2 + 4] = turnOpp;
                  board[input1][input2 + 5] = turnOpp;
                  board[input1][input2 + 6] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
              
              try
              {
                //For right diagonal top change
                if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turn && board[input1 - 5][input2 + 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                  board[input1 - 4][input2 + 4] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turn && board[input1 - 5][input2 + 5] == turn && board[input1 - 6][input2 + 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                  board[input1 - 4][input2 + 4] = turnOpp;
                  board[input1 - 5][input2 + 5] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turn && board[input1 - 5][input2 + 5] == turn && board[input1 - 6][input2 + 6] == turn && board[input1 - 7][input2 + 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                  board[input1 - 4][input2 + 4] = turnOpp;
                  board[input1 - 5][input2 + 5] = turnOpp;
                  board[input1 - 6][input2 + 6] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
              
              try
              {
                //For right diagonal down change
                if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turn && board[input1 + 5][input2 + 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                  board[input1 + 4][input2 + 4] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turn && board[input1 + 5][input2 + 5] == turn && board[input1 + 6][input2 + 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                  board[input1 + 4][input2 + 4] = turnOpp;
                  board[input1 + 5][input2 + 5] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turn && board[input1 + 5][input2 + 5] == turn && board[input1 + 6][input2 + 6] == turn && board[input1 + 7][input2 + 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                  board[input1 + 4][input2 + 4] = turnOpp;
                  board[input1 + 5][input2 + 5] = turnOpp;
                  board[input1 + 6][input2 + 6] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
              
              try
              {
                //For left diagonal top change
                if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turn && board[input1 - 5][input2 - 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                  board[input1 - 4][input2 - 4] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turn && board[input1 - 5][input2 - 5] == turn && board[input1 - 6][input2 - 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                  board[input1 - 4][input2 - 4] = turnOpp;
                  board[input1 - 5][input2 - 5] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turn && board[input1 - 5][input2 - 5] == turn && board[input1 - 6][input2 - 6] == turn && board[input1 - 7][input2 - 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                  board[input1 - 4][input2 - 4] = turnOpp;
                  board[input1 - 5][input2 - 5] = turnOpp;
                  board[input1 - 6][input2 - 6] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
              
              try
              {
                //For left diagonal top change
                if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turn && board[input1 + 5][input2 - 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                  board[input1 + 4][input2 - 4] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turn && board[input1 + 5][input2 - 5] == turn && board[input1 + 6][input2 - 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                  board[input1 + 4][input2 - 4] = turnOpp;
                  board[input1 + 5][input2 - 5] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turn && board[input1 + 5][input2 - 5] == turn && board[input1 + 6][input2 - 6] == turn && board[input1 + 7][input2 - 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                  board[input1 + 4][input2 - 4] = turnOpp;
                  board[input1 + 5][input2 - 5] = turnOpp;
                  board[input1 + 6][input2 - 6] = turnOpp;
                }
                else
                {
                  tryAgain++;
                }
              }
              catch (Exception e)
              {
                tryAgain++;
              }
            }
          }
        }
      }
      while (tryAgain == 8 || tryAgain == 15); //while tryagain is equal to 8 or 15
     
  }
 
  public static void playAi(int cpu) //This Ai goes though each possible move/location on the board and sees which one will have the best outcome in terms of enemey pieces fliped
  {
    int biggest , bigY , bigX;
    int [][] place = new int [8][8];
    int yAxis , xAxis;
    
    printBoard();
    
    System.out.println("Cpu is thinking..");
    
    try { //A delay so that you can see your move in play before it changes to what the cpu plays
    Thread.sleep(1500);                 //1000 milliseconds is one second.
} catch(InterruptedException ex) {
    Thread.currentThread().interrupt();
}
    
    for (int k = 0; k < 8; k++)
    {
      for (int h = 0; h < 8; h++)
      {
        if (board[k][h] == ' ') //so that the bot does not check spots where a piece already exists
        {
          yAxis = k; //sets the two numbers that are used to test the move
          xAxis = h;
          
          try //this try is to eliminate the error that occurs when the game logic trys to check a edge piece but goes outside the array
              {
                //For downwards change
                if (board[yAxis + 1][xAxis] == turn && board[yAxis + 2][xAxis] == turnOpp) //if there is an opponents piece inbetween the player's piece in the downwards direction
                {
                  place[yAxis][xAxis] ++; //if there can be a flip of a piece in this direction add 1 to the score of this position on the int array board
                }
                else if (board[yAxis + 1][xAxis] == turn && board[yAxis + 2][xAxis] == turn && board[yAxis + 3][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis + 1][xAxis] == turn && board[yAxis + 2][xAxis] == turn && board[yAxis + 3][xAxis] == turn && board[yAxis + 4][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis + 1][xAxis] == turn && board[yAxis + 2][xAxis] == turn && board[yAxis + 3][xAxis] == turn && board[yAxis + 4][xAxis] == turn && board[yAxis + 5][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis + 1][xAxis] == turn && board[yAxis + 2][xAxis] == turn && board[yAxis + 3][xAxis] == turn && board[yAxis + 4][xAxis] == turn && board[yAxis + 5][xAxis] == turn && board[yAxis + 6][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis + 1][xAxis] == turn && board[yAxis + 2][xAxis] == turn && board[yAxis + 3][xAxis] == turn && board[yAxis + 4][xAxis] == turn && board[yAxis + 5][xAxis] == turn && board[yAxis + 6][xAxis] == turn && board[yAxis + 7][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e) //for the exception of playing in corners or edges of board
              {
              }
              
              try
              {
                //For upwards change
                if (board[yAxis - 1][xAxis] == turn && board[yAxis - 2][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis - 1][xAxis] == turn && board[yAxis - 2][xAxis] == turn && board[yAxis - 3][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis - 1][xAxis] == turn && board[yAxis - 2][xAxis] == turn && board[yAxis - 3][xAxis] == turn && board[yAxis - 4][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis - 1][xAxis] == turn && board[yAxis - 2][xAxis] == turn && board[yAxis - 3][xAxis] == turn && board[yAxis - 4][xAxis] == turn && board[yAxis - 5][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis - 1][xAxis] == turn && board[yAxis - 2][xAxis] == turn && board[yAxis - 3][xAxis] == turn && board[yAxis - 4][xAxis] == turn && board[yAxis - 5][xAxis] == turn && board[yAxis - 6][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis - 1][xAxis] == turn && board[yAxis - 2][xAxis] == turn && board[yAxis - 3][xAxis] == turn && board[yAxis - 4][xAxis] == turn && board[yAxis - 5][xAxis] == turn && board[yAxis - 6][xAxis] == turn && board[yAxis - 7][xAxis] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
              try 
              {
                //for left change
                if (board[yAxis][xAxis - 1] == turn && board[yAxis][xAxis - 2] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis][xAxis - 1] == turn && board[yAxis][xAxis - 2] == turn && board[yAxis][xAxis - 3] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis][xAxis - 1] == turn && board[yAxis][xAxis - 2] == turn && board[yAxis][xAxis - 3] == turn && board[yAxis][xAxis - 4] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis][xAxis - 1] == turn && board[yAxis][xAxis - 2] == turn && board[yAxis][xAxis - 3] == turn && board[yAxis][xAxis - 4] == turn && board[yAxis][xAxis - 5] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis][xAxis - 1] == turn && board[yAxis][xAxis - 2] == turn && board[yAxis][xAxis - 3] == turn && board[yAxis][xAxis - 4] == turn && board[yAxis][xAxis - 5] == turn && board[yAxis][xAxis - 6] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis][xAxis - 1] == turn && board[yAxis][xAxis - 2] == turn && board[yAxis][xAxis - 3] == turn && board[yAxis][xAxis - 4] == turn && board[yAxis][xAxis - 5] == turn && board[yAxis][xAxis - 6] == turn && board[yAxis][xAxis - 7] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
              
              try 
              {
                //For right change
                if (board[yAxis][xAxis + 1] == turn && board[yAxis][xAxis + 2] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis][xAxis + 1] == turn && board[yAxis][xAxis + 2] == turn && board[yAxis][xAxis + 3] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis][xAxis + 1] == turn && board[yAxis][xAxis + 2] == turn && board[yAxis][xAxis + 3] == turn && board[yAxis][xAxis + 4] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis][xAxis + 1] == turn && board[yAxis][xAxis + 2] == turn && board[yAxis][xAxis + 3] == turn && board[yAxis][xAxis + 4] == turn && board[yAxis][xAxis + 5] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis][xAxis + 1] == turn && board[yAxis][xAxis + 2] == turn && board[yAxis][xAxis + 3] == turn && board[yAxis][xAxis + 4] == turn && board[yAxis][xAxis + 5] == turn && board[yAxis][xAxis + 6] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis][xAxis + 1] == turn && board[yAxis][xAxis + 2] == turn && board[yAxis][xAxis + 3] == turn && board[yAxis][xAxis + 4] == turn && board[yAxis][xAxis + 5] == turn && board[yAxis][xAxis + 6] == turn && board[yAxis][xAxis + 7] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For right diagonal top change
                if (board[yAxis - 1][xAxis + 1] == turn && board[yAxis - 2][xAxis + 2] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis - 1][xAxis + 1] == turn && board[yAxis - 2][xAxis + 2] == turn && board[yAxis - 3][xAxis + 3] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis - 1][xAxis + 1] == turn && board[yAxis - 2][xAxis + 2] == turn && board[yAxis - 3][xAxis + 3] == turn && board[yAxis - 4][xAxis + 4] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis - 1][xAxis + 1] == turn && board[yAxis - 2][xAxis + 2] == turn && board[yAxis - 3][xAxis + 3] == turn && board[yAxis - 4][xAxis + 4] == turn && board[yAxis - 5][xAxis + 5] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis - 1][xAxis + 1] == turn && board[yAxis - 2][xAxis + 2] == turn && board[yAxis - 3][xAxis + 3] == turn && board[yAxis - 4][xAxis + 4] == turn && board[yAxis - 5][xAxis + 5] == turn && board[yAxis - 6][xAxis + 6] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis - 1][xAxis + 1] == turn && board[yAxis - 2][xAxis + 2] == turn && board[yAxis - 3][xAxis + 3] == turn && board[yAxis - 4][xAxis + 4] == turn && board[yAxis - 5][xAxis + 5] == turn && board[yAxis - 6][xAxis + 6] == turn && board[yAxis - 7][xAxis + 7] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For right diagonal down change
                if (board[yAxis + 1][xAxis + 1] == turn && board[yAxis + 2][xAxis + 2] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis + 1][xAxis + 1] == turn && board[yAxis + 2][xAxis + 2] == turn && board[yAxis + 3][xAxis + 3] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis + 1][xAxis + 1] == turn && board[yAxis + 2][xAxis + 2] == turn && board[yAxis + 3][xAxis + 3] == turn && board[yAxis + 4][xAxis + 4] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis + 1][xAxis + 1] == turn && board[yAxis + 2][xAxis + 2] == turn && board[yAxis + 3][xAxis + 3] == turn && board[yAxis + 4][xAxis + 4] == turn && board[yAxis + 5][xAxis + 5] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis + 1][xAxis + 1] == turn && board[yAxis + 2][xAxis + 2] == turn && board[yAxis + 3][xAxis + 3] == turn && board[yAxis + 4][xAxis + 4] == turn && board[yAxis + 5][xAxis + 5] == turn && board[yAxis + 6][xAxis + 6] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis + 1][xAxis + 1] == turn && board[yAxis + 2][xAxis + 2] == turn && board[yAxis + 3][xAxis + 3] == turn && board[yAxis + 4][xAxis + 4] == turn && board[yAxis + 5][xAxis + 5] == turn && board[yAxis + 6][xAxis + 6] == turn && board[yAxis + 7][xAxis + 7] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For left diagonal top change
                if (board[yAxis - 1][xAxis - 1] == turn && board[yAxis - 2][xAxis - 2] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis - 1][xAxis - 1] == turn && board[yAxis - 2][xAxis - 2] == turn && board[yAxis - 3][xAxis - 3] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis - 1][xAxis - 1] == turn && board[yAxis - 2][xAxis - 2] == turn && board[yAxis - 3][xAxis - 3] == turn && board[yAxis - 4][xAxis - 4] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis - 1][xAxis - 1] == turn && board[yAxis - 2][xAxis - 2] == turn && board[yAxis - 3][xAxis - 3] == turn && board[yAxis - 4][xAxis - 4] == turn && board[yAxis - 5][xAxis - 5] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis - 1][xAxis - 1] == turn && board[yAxis - 2][xAxis - 2] == turn && board[yAxis - 3][xAxis - 3] == turn && board[yAxis - 4][xAxis - 4] == turn && board[yAxis - 5][xAxis - 5] == turn && board[yAxis - 6][xAxis - 6] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis - 1][xAxis - 1] == turn && board[yAxis - 2][xAxis - 2] == turn && board[yAxis - 3][xAxis - 3] == turn && board[yAxis - 4][xAxis - 4] == turn && board[yAxis - 5][xAxis - 5] == turn && board[yAxis - 6][xAxis - 6] == turn && board[yAxis - 7][xAxis - 7] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For left diagonal top change
                if (board[yAxis + 1][xAxis - 1] == turn && board[yAxis + 2][xAxis - 2] == turnOpp)
                {
                  place[yAxis][xAxis] ++;
                }
                else if (board[yAxis + 1][xAxis - 1] == turn && board[yAxis + 2][xAxis - 2] == turn && board[yAxis + 3][xAxis - 3] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 2;
                }
                else if (board[yAxis + 1][xAxis - 1] == turn && board[yAxis + 2][xAxis - 2] == turn && board[yAxis + 3][xAxis - 3] == turn && board[yAxis + 4][xAxis - 4] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 3;
                }
                else if (board[yAxis + 1][xAxis - 1] == turn && board[yAxis + 2][xAxis - 2] == turn && board[yAxis + 3][xAxis - 3] == turn && board[yAxis + 4][xAxis - 4] == turn && board[yAxis + 5][xAxis - 5] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 4;
                }
                else if (board[yAxis + 1][xAxis - 1] == turn && board[yAxis + 2][xAxis - 2] == turn && board[yAxis + 3][xAxis - 3] == turn && board[yAxis + 4][xAxis - 4] == turn && board[yAxis + 5][xAxis - 5] == turn && board[yAxis + 6][xAxis - 6] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 5;
                }
                else if (board[yAxis + 1][xAxis - 1] == turn && board[yAxis + 2][xAxis - 2] == turn && board[yAxis + 3][xAxis - 3] == turn && board[yAxis + 4][xAxis - 4] == turn && board[yAxis + 5][xAxis - 5] == turn && board[yAxis + 6][xAxis - 6] == turn && board[yAxis + 7][xAxis - 7] == turnOpp)
                {
                  place[yAxis][xAxis] = place[yAxis][xAxis] + 6;
                }
              }
              catch (Exception e)
              {
              }
          
        }
        else
        {
        }
        
        
      }
    }
    
    biggest = 0; //This is the variable that will hold the biggest place array number (aka the best possible spot on the board)
    bigY = 0; //this will be the coordinates of the best possible move
    bigX = 0;
    
    for (int y = 0; y < 8; y++) //goes through every element of (place) which is set up exactly like the real board but instead with integers
    {
      for (int x = 0; x < 8; x++)
      {
        if (place[y][x] > biggest) //if the selected spot on the board has a higher "score" then make it the biggest/best spot
        {
          biggest = place[y][x]; //sets the best place to the selected element in the array's score
          bigY = y; //sets the coordinates of that element
          bigX = x;
        }
      }
    }
    if (biggest == 0) //if there are no moves possible across the whole board resulting in all the scores of the elements of the place array being 0
    {
      bigY = 8; //make the coordinates 8,8
      bigX = 8; //this will later trigger a skip turn if statement
    }
    
    if (cpu == 1) //if user said yes to the cpu mode on
    {
      System.out.print("   ");
      for (int j = 0; j < 8; j ++)
      {
        System.out.print(j + "  ");
      }
      System.out.println("");
      for(int r = 0; r < 8; r++)
      {
        System.out.print(r + " ");
        for (int c = 0; c < 8; c++)
        {
          System.out.print("[" + place[r][c] + "]"); //Prints the place array aka the points of the moves in the same fashon the main board is printed
        }
        System.out.println("");
      }
    }
    
    
    input1 = bigY;
    input2 = bigX;
    System.out.println("The Ai is playing at:"); //tells the player where the Ai played
    System.out.println(bigY + ", " + bigX);
    
      
          if (input1 == 8 && input2 == 8) //if both inputs are equal to 8
          {
            System.out.println("Ai cannot play, skipping it's turn"); //skips the ai turn
          }
          else
          {
              try //this try is to eliminate the error that occurs when the game logic trys to check a edge piece but goes outside the array
              {
                //For downwards change
                if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turnOpp) //if there is an opponents piece inbetween the player's piece in the downwards direction
                {
                  board[input1][input2] = turnOpp; //set a piece on the position identified by the player
                  board[input1 + 1][input2] = turnOpp; //set the piece that is inbtween
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turn && board[input1 + 5][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                  board[input1 + 4][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turn && board[input1 + 5][input2] == turn && board[input1 + 6][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                  board[input1 + 4][input2] = turnOpp;
                  board[input1 + 5][input2] = turnOpp;
                }
                else if (board[input1 + 1][input2] == turn && board[input1 + 2][input2] == turn && board[input1 + 3][input2] == turn && board[input1 + 4][input2] == turn && board[input1 + 5][input2] == turn && board[input1 + 6][input2] == turn && board[input1 + 7][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2] = turnOpp;
                  board[input1 + 2][input2] = turnOpp;
                  board[input1 + 3][input2] = turnOpp;
                  board[input1 + 4][input2] = turnOpp;
                  board[input1 + 5][input2] = turnOpp;
                  board[input1 + 6][input2] = turnOpp;
                }

              }
              catch (Exception e) //for the exception of playing in corners or edges of board
              {
              }
              
              try
              {
                //For upwards change
                if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turn && board[input1 - 5][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                  board[input1 - 4][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turn && board[input1 - 5][input2] == turn && board[input1 - 6][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                  board[input1 - 4][input2] = turnOpp;
                  board[input1 - 5][input2] = turnOpp;
                }
                else if (board[input1 - 1][input2] == turn && board[input1 - 2][input2] == turn && board[input1 - 3][input2] == turn && board[input1 - 4][input2] == turn && board[input1 - 5][input2] == turn && board[input1 - 6][input2] == turn && board[input1 - 7][input2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2] = turnOpp;
                  board[input1 - 2][input2] = turnOpp;
                  board[input1 - 3][input2] = turnOpp;
                  board[input1 - 4][input2] = turnOpp;
                  board[input1 - 5][input2] = turnOpp;
                  board[input1 - 6][input2] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
              try 
              {
                //for left change
                if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turn && board[input1][input2 - 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                  board[input1][input2 - 4] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turn && board[input1][input2 - 5] == turn && board[input1][input2 - 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                  board[input1][input2 - 4] = turnOpp;
                  board[input1][input2 - 5] = turnOpp;
                }
                else if (board[input1][input2 - 1] == turn && board[input1][input2 - 2] == turn && board[input1][input2 - 3] == turn && board[input1][input2 - 4] == turn && board[input1][input2 - 5] == turn && board[input1][input2 - 6] == turn && board[input1][input2 - 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 - 1] = turnOpp;
                  board[input1][input2 - 2] = turnOpp;
                  board[input1][input2 - 3] = turnOpp;
                  board[input1][input2 - 4] = turnOpp;
                  board[input1][input2 - 5] = turnOpp;
                  board[input1][input2 - 6] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
              
              try 
              {
                //For right change
                if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turn && board[input1][input2 + 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                  board[input1][input2 + 4] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turn && board[input1][input2 + 5] == turn && board[input1][input2 + 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                  board[input1][input2 + 4] = turnOpp;
                  board[input1][input2 + 5] = turnOpp;
                }
                else if (board[input1][input2 + 1] == turn && board[input1][input2 + 2] == turn && board[input1][input2 + 3] == turn && board[input1][input2 + 4] == turn && board[input1][input2 + 5] == turn && board[input1][input2 + 6] == turn && board[input1][input2 + 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1][input2 + 1] = turnOpp;
                  board[input1][input2 + 2] = turnOpp;
                  board[input1][input2 + 3] = turnOpp;
                  board[input1][input2 + 4] = turnOpp;
                  board[input1][input2 + 5] = turnOpp;
                  board[input1][input2 + 6] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For right diagonal top change
                if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turn && board[input1 - 5][input2 + 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                  board[input1 - 4][input2 + 4] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turn && board[input1 - 5][input2 + 5] == turn && board[input1 - 6][input2 + 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                  board[input1 - 4][input2 + 4] = turnOpp;
                  board[input1 - 5][input2 + 5] = turnOpp;
                }
                else if (board[input1 - 1][input2 + 1] == turn && board[input1 - 2][input2 + 2] == turn && board[input1 - 3][input2 + 3] == turn && board[input1 - 4][input2 + 4] == turn && board[input1 - 5][input2 + 5] == turn && board[input1 - 6][input2 + 6] == turn && board[input1 - 7][input2 + 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 + 1] = turnOpp;
                  board[input1 - 2][input2 + 2] = turnOpp;
                  board[input1 - 3][input2 + 3] = turnOpp;
                  board[input1 - 4][input2 + 4] = turnOpp;
                  board[input1 - 5][input2 + 5] = turnOpp;
                  board[input1 - 6][input2 + 6] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For right diagonal down change
                if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turn && board[input1 + 5][input2 + 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                  board[input1 + 4][input2 + 4] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turn && board[input1 + 5][input2 + 5] == turn && board[input1 + 6][input2 + 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                  board[input1 + 4][input2 + 4] = turnOpp;
                  board[input1 + 5][input2 + 5] = turnOpp;
                }
                else if (board[input1 + 1][input2 + 1] == turn && board[input1 + 2][input2 + 2] == turn && board[input1 + 3][input2 + 3] == turn && board[input1 + 4][input2 + 4] == turn && board[input1 + 5][input2 + 5] == turn && board[input1 + 6][input2 + 6] == turn && board[input1 + 7][input2 + 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 + 1] = turnOpp;
                  board[input1 + 2][input2 + 2] = turnOpp;
                  board[input1 + 3][input2 + 3] = turnOpp;
                  board[input1 + 4][input2 + 4] = turnOpp;
                  board[input1 + 5][input2 + 5] = turnOpp;
                  board[input1 + 6][input2 + 6] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For left diagonal top change
                if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turn && board[input1 - 5][input2 - 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                  board[input1 - 4][input2 - 4] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turn && board[input1 - 5][input2 - 5] == turn && board[input1 - 6][input2 - 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                  board[input1 - 4][input2 - 4] = turnOpp;
                  board[input1 - 5][input2 - 5] = turnOpp;
                }
                else if (board[input1 - 1][input2 - 1] == turn && board[input1 - 2][input2 - 2] == turn && board[input1 - 3][input2 - 3] == turn && board[input1 - 4][input2 - 4] == turn && board[input1 - 5][input2 - 5] == turn && board[input1 - 6][input2 - 6] == turn && board[input1 - 7][input2 - 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 - 1][input2 - 1] = turnOpp;
                  board[input1 - 2][input2 - 2] = turnOpp;
                  board[input1 - 3][input2 - 3] = turnOpp;
                  board[input1 - 4][input2 - 4] = turnOpp;
                  board[input1 - 5][input2 - 5] = turnOpp;
                  board[input1 - 6][input2 - 6] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
              
              try
              {
                //For left diagonal top change
                if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turn && board[input1 + 5][input2 - 5] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                  board[input1 + 4][input2 - 4] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turn && board[input1 + 5][input2 - 5] == turn && board[input1 + 6][input2 - 6] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                  board[input1 + 4][input2 - 4] = turnOpp;
                  board[input1 + 5][input2 - 5] = turnOpp;
                }
                else if (board[input1 + 1][input2 - 1] == turn && board[input1 + 2][input2 - 2] == turn && board[input1 + 3][input2 - 3] == turn && board[input1 + 4][input2 - 4] == turn && board[input1 + 5][input2 - 5] == turn && board[input1 + 6][input2 - 6] == turn && board[input1 + 7][input2 - 7] == turnOpp)
                {
                  board[input1][input2] = turnOpp;
                  board[input1 + 1][input2 - 1] = turnOpp;
                  board[input1 + 2][input2 - 2] = turnOpp;
                  board[input1 + 3][input2 - 3] = turnOpp;
                  board[input1 + 4][input2 - 4] = turnOpp;
                  board[input1 + 5][input2 - 5] = turnOpp;
                  board[input1 + 6][input2 - 6] = turnOpp;
                }
              }
              catch (Exception e)
              {
              }
            }
     
  }
  public static void printBoard() //This method prints the board
  {
    
    System.out.print("   ");
    for (int j = 0; j < 8; j ++)
    {
      System.out.print(j + "  ");
    }
    System.out.println("");
    for(int r = 0; r < 8; r++)
    {
      System.out.print(r + " ");
      for (int c = 0; c < 8; c++)
      {
        System.out.print("[" + board[r][c] + "]"); //Prints the board array which is universal and that has been updated by the gamelogic
      }
      System.out.println("");
    }
  }
  
public static void rules() //The medthod that explains the rules of othello
  {
    System.out.println("---------Welcome to OTHELLO---------");
    System.out.println("How to play:");
    System.out.println("The objective of the game is to have the majority of your pieces(either 'x' or 'o') on the 8x8 board at the end of the game");
    System.out.println("Each turn you can place one piece, place your piece on an empty square so that one (or more) of the opponent's pieces are between yours.");
    System.out.println("All of the opponents pieces between the piece you placed and another of your piece will turn into yours");
    System.out.println("This is an example of a move:"); //an example for better understanding
    System.out.println("   0  1  2  3  4  5  6  7 ") ; 
    System.out.println("0 [ ][ ][ ][ ][ ][ ][ ][ ] ");
    System.out.println("1 [ ][ ][ ][o][ ][ ][o][ ] ");
    System.out.println("2 [ ][ ][ ][x][ ][x][ ][ ] ");
    System.out.println("3 [ ][ ][ ][x][x][ ][ ][ ] ");
    System.out.println("4 [ ][ ][ ][o][x][x][x][o] ");
    System.out.println("5 [ ][ ][ ][|][ ][ ][ ][ ] ");
    System.out.println("6 [ ][ ][ ][|][ ][ ][ ][ ] ");
    System.out.println("7 [ ][ ][ ][|][ ][ ][ ][ ] ");
    System.out.println("if you placed a piece where the lines indicate this would be the result:");
    System.out.println("   0  1  2  3  4  5  6  7 ") ; 
    System.out.println("0 [ ][ ][ ][ ][ ][ ][ ][ ] ");
    System.out.println("1 [ ][ ][ ][o][ ][ ][o][ ] ");
    System.out.println("2 [ ][ ][ ][o][ ][o][ ][ ] ");
    System.out.println("3 [ ][ ][ ][o][o][ ][ ][ ] ");
    System.out.println("4 [ ][ ][ ][o][o][o][o][o] ");
    System.out.println("5 [ ][ ][ ][ ][ ][ ][ ][ ] ");
    System.out.println("6 [ ][ ][ ][ ][ ][ ][ ][ ] ");
    System.out.println("7 [ ][ ][ ][ ][ ][ ][ ][ ] ");
  }
 public static boolean check()
  {
    //checks if the piece is being placed in a empty slot
    boolean check = false;
    if (board[input1][input2] == ' ')
    {
      check = true;
    }
    else
    {
       check = false;
      System.out.println("You cannot play on other pieces.");
    }
    
    return check;
  }

}


  
