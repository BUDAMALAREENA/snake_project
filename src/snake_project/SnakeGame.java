package snake_project;
import javax.swing.*;



public class SnakeGame {
   public static void main(String[] args) {
       // Create the game window
       JFrame frame = new JFrame("Snake Game");
       GameBoard board = new GameBoard();

       // Add the game board to the window
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(600, 600);
       frame.add(board);
       frame.setVisible(true);
       
       // Start the game
       board.startGame();
   }
}
////


