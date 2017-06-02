class Othello
{
  public static char[][] board = new char[8][8];
  public static void main (String [] args) //calls all needed methods
  {
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
      
      printBoard();
    int input1, input2;
    input1 = In.getInt();
    input2 = In.getInt();
   
    /*
    if (check(input) = true)
    {
  
    }*/
  }
  public static void rules()
  {
    System.out.println("---------Welcome to OTHELLO---------");
      System.out.println("How to play:");
      System.out.println("The objective of the game is to have the majority of your pieces(either 'x' or 'o') on the 8x8 board at the end of the game");
      System.out.println("Each turn you can place one piece, place your piece on an empty square so that one (or more) of the opponent's pieces are between yours.");
      System.out.println("All of the opponents pieces between the piece you placed and another of your piece will turn into yours");
      System.out.println("This is an example of a move:");
      System.out.println("0  1  2  3  4  5  6  7 ") ; 
      System.out.println("0 [ ][ ][ ][ ][ ][ ][ ][ ] ");
      System.out.println("1 [ ][ ][ ][o][ ][ ][o][ ] ");
      System.out.println("2 [ ][ ][ ][x][ ][x][ ][ ] ");
      System.out.println("3 [ ][ ][ ][x][x][ ][ ][ ] ");
      System.out.println("4 [ ][ ][ ][o][x][x][x][o] ");
      System.out.println("5 [ ][ ][ ][|][ ][ ][ ][ ] ");
      System.out.println("6 [ ][ ][ ][|][ ][ ][ ][ ] ");
      System.out.println("7 [ ][ ][ ][|][ ][ ][ ][ ] ");
      System.out.println("if you placed a piece where the lines indicate this would be the result:");
      System.out.println("0 [ ][ ][ ][ ][ ][ ][ ][ ] ");
      System.out.println("1 [ ][ ][ ][o][ ][ ][o][ ] ");
      System.out.println("2 [ ][ ][ ][o][ ][o][ ][ ] ");
      System.out.println("3 [ ][ ][ ][o][o][ ][ ][ ] ");
      System.out.println("4 [ ][ ][ ][o][o][o][o][o] ");
      System.out.println("5 [ ][ ][ ][|][ ][ ][ ][ ] ");
      System.out.println("6 [ ][ ][ ][|][ ][ ][ ][ ] ");
      System.out.println("7 [ ][ ][ ][|][ ][ ][ ][ ] ");
  }
  public static boolean check(String input)
  {
    boolean check;
    check = false;
    //checks the input for invalid entries
    return check;
  }
  public static void player1(int input1, int input2)
  {
    //all for when player 1 plays (the 'o's)
    //sets what pieces need to be changed
    //through many for loops and if statements
  }
  public static void player2(int input1, int input2)
  {
    //all for when player 2 plays (the 'x's)
    //sets what pieces need to be changed
    //through many for loops and if statements
  }
  public static void printBoard()
  {
    board[3][3] = 'x';
    board[4][4] = 'x';
    board[3][4] = 'o';
    board[4][3] = 'o';
    
    System.out.print("  ");
    for (int j = 0; j < 8; j ++)
    {
      System.out.print(j + "  ");
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
  
  
  
  
  
  
}