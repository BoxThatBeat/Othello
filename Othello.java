class Othello
{
  public static char turn, turnOpp;
  public static int input1, input2;
  public static char[][] board = new char[8][8];
  public static void main (String [] args) //calls all needed methods
  {
    for (int k = 0; k < 8; k++)
    { 
      for (int h = 0; h < 8; h++)
      {
        board[k][h] = ' ';
      }
    }  
    int innerCheck = 0;
    int endCondition = 0;
    String in;
    System.out.println("Do you know how to play Othello?");
    System.out.println("type n to display instructions");
    in = In.getString();
    in = in.toUpperCase();
    if (in.equals ("N"))
    {
      rules();
    }
    else{}
    
    System.out.println("Let's Start");
    System.out.println("Player 1 you are the 'o's");
    System.out.println("Player 2 you are the 'x's");
    
    
    System.out.println("type in the row then the column");
    do
    {
      System.out.println("Player 1 it is your turn");
      
      turn = 'o';
      turnOpp = 'x';
      player1();
      
      endCondition++;
      
      System.out.println("Player 2 it is your turn");

      turn = 'o';
      turnOpp = 'o';
      player2();
      
      printBoard();
      
      endCondition++;
    }
    while (endCondition != 20);
  }
  
  public static void player1()
  {
    boolean tryAgain = false;
    //for when player 1 plays (the 'o's)
      do
      {
        tryAgain = false;
        printBoard();
        input1 = In.getInt();
        input2 = In.getInt();
        if (check() == false)
        {
          tryAgain = true;
        }
        else
        {
          board[input1][input2] = 'o';
          
          //replace All this with a for loop
          //
          //IMPORTANT
          //
          if (board[input1 + 1][input2] == 'x' && board[input1 + 2][input2] == 'o')
          {
            board[input1 + 1][input2] = 'o';
          }
          else if (board[input1 + 1][input2] == 'x' && board[input1 + 2][input2] == 'x' && board[input1 + 3][input2] == 'o')
          {
            board[input1 + 1][input2] = 'o';
            board[input1 + 2][input2] = 'o';
          }
          else if (board[input1 + 1][input2] == 'x' && board[input1 + 2][input2] == 'x' && board[input1 + 3][input2] == 'x' && board[input1 + 4][input2] == 'o')
          {
            board[input1 + 1][input2] = 'o';
            board[input1 + 2][input2] = 'o';
            board[input1 + 3][input2] = 'o';
          }
          else if (board[input1 + 1][input2] == 'x' && board[input1 + 2][input2] == 'x' && board[input1 + 3][input2] == 'x' && board[input1 + 4][input2] == 'x' && board[input1 + 5][input2] == 'o')
          {
            board[input1 + 1][input2] = 'o';
            board[input1 + 2][input2] = 'o';
            board[input1 + 3][input2] = 'o';
            board[input1 + 4][input2] = 'o';
          }
          else if (board[input1 + 1][input2] == 'x' && board[input1 + 2][input2] == 'x' && board[input1 + 3][input2] == 'x' && board[input1 + 4][input2] == 'x' && board[input1 + 5][input2] == 'x' && board[input1 + 6][input2] == 'o')
          {
            board[input1 + 1][input2] = 'o';
            board[input1 + 2][input2] = 'o';
            board[input1 + 3][input2] = 'o';
            board[input1 + 4][input2] = 'o';
            board[input1 + 5][input2] = 'o';
          }
          else if (board[input1 + 1][input2] == 'x' && board[input1 + 2][input2] == 'x' && board[input1 + 3][input2] == 'x' && board[input1 + 4][input2] == 'x' && board[input1 + 5][input2] == 'x' && board[input1 + 6][input2] == 'x' && board[input1 + 7][input2] == 'o')
          {
            board[input1 + 1][input2] = 'o';
            board[input1 + 2][input2] = 'o';
            board[input1 + 3][input2] = 'o';
            board[input1 + 4][input2] = 'o';
            board[input1 + 5][input2] = 'o';
            board[input1 + 6][input2] = 'o';
          }
          
        }
      }
      while (tryAgain); //while tryagain is true
      
    }
  public static void player2()
  {
       boolean tryAgain = false;
    //for when player 1 plays (the 'o's)
      do
      {
        tryAgain = false;
        printBoard();
        input1 = In.getInt();
        input2 = In.getInt();
        if (check() == false)
        {
          tryAgain = true;
        }
        else
        {
          board[input1][input2] = 'x';
        }
      }
      while (tryAgain); //while tryagain is true
  }
  public static void printBoard()
  {
    board[3][3] = 'x';
    board[4][4] = 'x';
    board[3][4] = 'o';
    board[4][3] = 'o';
    
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
        System.out.print("[" + board[r][c] + "]");
      }
      System.out.println("");
    }
  }
  
public static void rules()
  {
    System.out.println("---------Welcome to OTHELLO---------");
    System.out.println("How to play:");
    System.out.println("The objective of the game is to have the majority of your pieces(either 'x' or 'o') on the 8x8 board at the end of the game");
    System.out.println("Each turn you can place one piece, place your piece on an empty square so that one (or more) of the opponent's pieces are between yours.");
    System.out.println("All of the opponents pieces between the piece you placed and another of your piece will turn into yours");
    System.out.println("This is an example of a move:");
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
    System.out.println("5 [ ][ ][ ][|][ ][ ][ ][ ] ");
    System.out.println("6 [ ][ ][ ][|][ ][ ][ ][ ] ");
    System.out.println("7 [ ][ ][ ][|][ ][ ][ ][ ] ");
  }
 public static boolean check()
  {
    //checks the input for invalid entries
    boolean check = false;
    if (board[input1][input2] == ' ')
    {
      check = true;
    }
    else
    {
       check = false;
      System.out.println("You cannot play on other pieces.");
      System.out.println("Try again:");
    }
    
    return check;
  }




  
}
