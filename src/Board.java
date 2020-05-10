/* Liam Vukasinovic
 * May 31 2019
 * This is the class that creates the board. The board is created by making a 2d arrary of cells.
 * Each spot in the array is from the cell class. This class assigns values to each cell and sets
 * up the game before everything is put together
 */

import javax.swing.*; //Used mainly for buttons in this class

public class Board {
  private Cell[][] board; //Creates a 2d array for the board
  private int tilesLeft; //Checks how many mines are left
  private boolean loss; //Checks if you have lost yet
  private int fRow, fCol; //Values for the row/col that lost the user the game
  
  //Assigns all varibles and gets the board started
  public Board(){
    board = new Cell[9][9]; //Creates the game board
    loss = false;
    tilesLeft = 81;
    assignBoard(); //Calls three functions to assign values to the board
    setMines();
    finishBoard();
  }
  
  //Resets the cells when a new game has been created
  public void resetCells(){
    for (int k = 0; k < board.length;k++){
      for(int j = 0;j < board.length;j++){
        board[k][j].reset(); //Resets the current cell
      }
    }
  }
  
  //Assigns every value in the array to a cell
  public void assignBoard(){
    for (int k = 0; k < board.length;k++){
      for(int j = 0;j < board.length;j++){
        board[k][j] = new Cell(); //Creates a new cell
        board[k][j].assignSprites(); //Adds the sprites reletave to the cells value
        board[k][j].setID(Integer.toString(k)+Integer.toString(j)); //Sets id, used for getting user input
      }
    }
  }
  
  //Sets 10 random cells to be a bomb
  public void setMines(){
    int k = 0; //For exiting the loop
    while (k <= 9){
      //Creates 2 random numbers for assigning mines
      int a = (int)(Math.random() * 9);
      int b = (int)(Math.random() * 9);
      if (board[a][b].mineOrNot() == false){
        board[a][b].setMine(); 
        k++;
      }
    }
  }
  
  //Asssigns each cell a value for how many mines is touching it
  public void assignValues(int a, int b){
    for (int k = -1; k <= 1; k++){
      for (int j = -1; j <= 1;j++){
        try{ //Checks if the array is inbounds or not
          if (board[a + k][b + j].mineOrNot() && board[a][b].mineOrNot() == false){
            board[a][b].increaseValue(); //Since bomb is touching it increaes value
          }
        }catch(Exception e){} //Does the above for every value while it is not out of the array
      }
    }
  }
  
  //Finishes assinging all values to the board
  public void finishBoard(){
    for (int k = 0; k < board.length; k++){
      for (int j = 0; j < board.length;j++){
        assignValues(k,j); //Finishes assigning values of board
      }   
    }
  }
  
  //Gets the buttons for each cell
  public JButton getButtons(int k, int j){
    return board[k][j].returnButton(); //Returns the current cells button
  }
  
  //Flags a cell on the board
  public void flagCell(int row, int col){
    board[row][col].flag(); //Flags the cell the user clicks on
  }
  
  //Recursivly reveals empty cells, or just reveals a cell
  public void revealEmptys(int row, int col){
    //Checks if array is out of bounds, and if it has been revealed yet
    if (row < 0 || row > board.length-1 || col < 0 || col > board.length-1 || board[row][col].isRevealed()){
      return; //Exits recursive loop
    }
    
    board[row][col].revealCell(false); //Reveal the cell the user clicked on
    tilesLeft--; //Decreases how many tiles are left, used for checking if user has won
    if (board[row][col].mineOrNot()){
      loss = true; //Sets the game to be finished
      fRow = row; //Stores the row which lost the game
      fCol = col; //Stores the col that lost the game
    }
    
    //If cell has no value, recursivly reveals empty cells around it
    if (board[row][col].getValue() == 0){
      revealEmptys(row+1,col);
      revealEmptys(row-1,col);
      revealEmptys(row,col+1);
      revealEmptys(row,col-1);
    }
  }
  
  //Reveals all cells if you have lost/won
  public void revealAll(){
    for (int k = 0; k < board.length; k++){
      for (int j = 0; j < board.length;j++){
        if (k == fRow && j == fCol){
          board[k][j].revealCell(false);
        }else{ //Try and fix this
          board[k][j].revealCell(true);
        }
      }
    }
  }
  
  //Checks if game is over
  public String isGameOver(){
    if (loss){ //Checks if you have lost
      revealAll();
      return "L";
    }else if(tilesLeft == 10){
      for (int k = 0; k < board.length; k++){
        for (int j = 0; j < board.length;j++){
          board[k][j].revealCell(true);
        }
      }
      return "W"; //Returns the game has been won
    }    
    return "N"; //Returns no winner for the game
  }
}
