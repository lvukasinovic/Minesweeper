/* Liam Vukasinovic
 * May 31 2019
 * This is the cell class. This creates cells for the 2d array that is created
 * later that sets up the game board. Each cell has images with it, a value for
 * how many bombs is touching is, values to check is it has been revealed or if
 * a mine is touching it and much more.
 */ 

import javax.swing.*; //Used mainly for buttons in this class

public class Cell {
  
  private boolean isRevealed; //Checks if cell has been clicked on or not
  private int value; //Value of cell/how many bombs are around it
  private boolean isMine; //Checks if cell is a mine
  private JButton cell; //Creates a button for each cell
  private Icon tile = new ImageIcon("tile.png"); //Default icon for the tile
  private Icon flag = new ImageIcon("flag.png");
  private Icon[] values; //Array to store value icons
  private Icon[] bombs; //Stores the images for bombsa
  private boolean touchingMine; //Varible for checking if a cell is a mine
  private boolean isFlagged; //Checks if a cell is currently flagged
  
  //Construtor for the cells, assigns the varibles values;
  public Cell(){
    isRevealed = false;
    isMine = false;
    cell = new JButton(tile);
    touchingMine = false;
    value = 0;
    values = new Icon[9];
    bombs = new Icon[2];
    isFlagged = false;
  }
  
  //Method for setting the cell to a mine
  public void setMine(){
    isMine = true; 
    value = -1; //For reference in other methods
  }
  
  //Assigns the sprites for the game
  public void assignSprites(){
    //Adds to the array for the values of cells
    for (int k = 0;k < values.length;k++){
      values[k] = new ImageIcon(Integer.toString(k) + ".png"); //Uses for loop to get file
    }
    //Adds to the array for sprites of the bombs
    for (int j = 0; j < bombs.length;j++){
      bombs[j] = new ImageIcon("bomb" + Integer.toString(j+1) + ".png"); //Uses for loop to get file
    }
  }
  
  //Method for checking if a cell is a mine
  public boolean mineOrNot(){
    return isMine; //Returns if it is a mine or not
  }
  
  //Increasing the value of how many mines are touching
  public void increaseValue(){
    value++;
    touchingMine = true;
  }
  
  //Returns a cells value
  public int getValue(){
    return value; //Returns the value
  }
  
  //Returns the cells button
  public JButton returnButton(){
    return cell;
  }
  
  //Sets the ID/Name
  public void setID(String id){
    cell.setName(id);
  }
  
  //Gets the cells ID/Name
  public String getID(){
    return cell.getName(); 
  }
  
  //Changes to/from flag icon
  public void flag(){
    if (isFlagged){ //If already flagged changes back to a tile
      cell.setIcon(tile); //Changes back the the regular tile icon
      isFlagged = false; //Not flagged anymore
    }else{ //Otherwise flags the cell
      cell.setIcon(flag); 
      isFlagged = true;
    }
  }
  
  //Checks if a cell has been clicked on yet
  public boolean isRevealed(){
    return isRevealed; 
  }
  
  //Resets the game board
  public void reset(){
    //Resets all the inital varibles that are created
    isRevealed = false;
    isMine = false;
    cell = new JButton(tile);
    touchingMine = false;
    value = 0;
    isFlagged = false;
  }
  
  //Reveals what is under a cell
  public void revealCell(boolean firstBomb){
    if (!mineOrNot()){ //Checks if it is not a mine
      cell.setIcon(values[value]);
      cell.setDisabledIcon(values[value]);
    }else if(!firstBomb){ //If it is the first bomb revealed, it uses a different sprite
      cell.setIcon(bombs[1]);
      cell.setDisabledIcon(bombs[1]);
    }else{ //Reveals bombs with different sprite
      cell.setIcon(bombs[0]);
      cell.setDisabledIcon(bombs[0]);
    }
    cell.setEnabled(false); //Disables the button
    isRevealed = true; //Tells the program it has been revealed
  }
}