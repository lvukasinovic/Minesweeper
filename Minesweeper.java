/* Liam Vukasinovic
 * May 31 2019
 * This is the Minesweeper/game class. Everything that has to do with creating
 * the graphics and game will be in this file. In this file is also the mouselistener
 * that is used for checking if the user has done anything in the game. Every input
 * is taken care of in the mousePressed method.
 */ 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JFrame implements MouseListener {
  
  //Global varibles for this class, mainly for graphics
  JFrame screen; //Creates the JFrame
  JButton play; //Restarts the game
  ImageIcon mainMenu = new ImageIcon ("mainMenu.png"); //Image for the main menu
  Board board = new Board(); //Creates the board for the game
  boolean hasWon = false; //Checks if user has won
  boolean start = false; //Checks if game has started
  int row, col; //Gets the row and col of each user click
  JButton begin, howToPlay, options, exit; //Buttons for main menu
  JButton back; //Buttons to go back to main menu
  
  
  public Minesweeper(){
    //Creates screen and container
    screen = new JFrame("Minesweeper");
    screen.setSize(500,555);
    Container content = screen.getContentPane();
    screen.setVisible(true);
    
    //Starts to build the game screen
    content.setLayout(new GridLayout(10,9));
    play = new JButton(new ImageIcon("face.png"));
    play.addMouseListener(this);
    JLabel[] panelHolder = new JLabel[9]; //Foramts the top of the screen    
    for(int m = 0; m < 9; m++) {
      if(m != 4){
        panelHolder[m] = new JLabel(new ImageIcon("tile.png"));
        content.add(panelHolder[m]);
      }else{
        content.add(play); 
      }
    }
    
    //Adds the cells for the game
    for (int k = 0; k < 9;k++){
      for(int j = 0;j < 9;j++){
        content.add(board.getButtons(k,j));
        board.getButtons(k,j).addMouseListener(this); //fix
      }
    }
    //Sets the screen visible again
    screen.setVisible(true);  
  }
  
  //Checks if mouse pressed
  //Flags, clicks bombs, and can restart game
  public void mousePressed(MouseEvent e){
    //Awful code incoming im sorry :(
    if(e.getSource() != play){
      row = Character.getNumericValue(((JComponent)e.getSource()).getName().charAt(0));
      col = Character.getNumericValue(((JComponent)e.getSource()).getName().charAt(1));
    }
    
    //restarts game
    if(e.getSource() == play){
      board.resetCells(); //Restarts cells
      screen.dispose(); //Clears old screen
      Minesweeper newGame = new Minesweeper(); //Makes new game without main menu
    }else if (e.getButton() == MouseEvent.BUTTON1){
      board.revealEmptys(row,col); //Reveals a cell
    }else if(e.getButton() == MouseEvent.BUTTON3){
      board.flagCell(row,col); //Flags a cell
    }
    if (board.isGameOver().equals("L")){ //Checks if you have lost
      play.setIcon(new ImageIcon("lost.png"));
    }else if(board.isGameOver().equals("W")){ //Checks if you have won
      play.setIcon(new ImageIcon("win.png"));
    }
  }
  
  public void mouseClicked(MouseEvent e){ 
  }
  public void mouseEntered(MouseEvent e){
  }
  public void mouseExited(MouseEvent e){
  }
  public void mouseReleased(MouseEvent e){
  }
}