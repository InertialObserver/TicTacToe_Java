import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

import javax.swing.border.SoftBevelBorder;

/**
     Jessica Iler
		 CSE 223: Spring 2017
		 May 18, 2017

		 Tic Tac Toe Game: Java game of classic "Tic Tac Toe"
*/

/**
		 Program Notes:
		 * On my system (Mac, running from command line) the buttons display and work as I expect.
		 When I transferred the program to Eclipse, the buttons dislay in a different order than expected and I'm unsure
		 of whether the functions are also reversed in that case. In the code, some of the buttons appear to be coded backwards
		 such as "full stop" being the "YES_OPTION". However when the program runs it reacts as expected.
		 I suspect this could be an issue on other systems, since it appears to be an issue on my Windows system from eclipse.

		 * The program runs and performs as expected on my Mac running from the command line.
		 When transferred to eclipse, it does not run. I spent a lot of time troubleshooting, and determined the problem
		 seems to be related to using the lambda in the event listener and possibly other issues with EventQueue.
		 I was unable to resolve the conflicts, so it doesn't run on Eclipse.

		 * Graphics and Paint are not used. My intention was to add those components once the rest worked fine.
		 I spent some time exploring those classes/methods and wish I did have more time to write more code to
		 implement - this is a fun program to write.

		 * To integrate the available choices, I decided to create different game types depending on which
		 choices the user picked. I found the second two game types more challenging because of how I set things up,
		 and didn't end up getting those working as intended. So I refactored to only include the first two types.

*/


public class TicTacToe extends JFrame {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe window = new TicTacToe();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;
	private JButton btnA1, btnA2, btnA3, btnB1, btnB2, btnB3, btnC1, btnC2, btnC3;
	private GameBoard gameBoard;

	/**
	 * Create the application.
	 */
   public TicTacToe() {
		 int start = this.startGame();
     int mark = this.getMarkChoice();
     int turn = this.getTurnChoice();

     if(start == 1)
     {
       if(turn == 1 && mark == 1) // human first, "X"
       {
         initializeGame1();
       } else if(turn == 1 && mark == 2) // human first, "O"
       {
         initializeGame2();
       } else if(turn == 2 && mark == 1) // computer first, human is "X"
       {
         initializeGame1(); // Game type 3 is non-functional, so this condition results in Game type 1 temporarily
       } else if(turn == 2 && mark == 2) // computer first, human is "O"
       {
         initializeGame2();// Game type 4 is non-functional, so this condition results in Game type 1 temporarily
       }
     }
   }

	 public int startGame()
		 {
			 Object[] options = { "FULL STOP",  // "Full Stop": exit program
														"ENGAGE"};  // "Engage": move on to choosing marker

			 int startChoice = JOptionPane.showOptionDialog(frame, "Mission Tic-Tac-Toe: Defeat the Borg",
																														"The game is afoot!",
																														JOptionPane.YES_NO_OPTION,
																														JOptionPane.PLAIN_MESSAGE,
																														null,
																														options,
																														options[1]);
		 if(startChoice == JOptionPane.YES_OPTION){
			 System.exit(0);
		 }
		 return 1;
	 }

	 public int getMarkChoice()
		 {
			 Object[] options = { "O",
														"X",
														"You Pick"};
			 int mark = 0;
			 int markChoice = JOptionPane.showOptionDialog(frame, "Ex's or Oh's?",
																														"Always two there are, no more, no less",
																														JOptionPane.YES_NO_CANCEL_OPTION,
																														JOptionPane.QUESTION_MESSAGE,
																														null,
																														options,
																														options[2]);
			 if(markChoice == JOptionPane.YES_OPTION){
				 mark = 2;  // "O"
			 } else if(markChoice == JOptionPane.NO_OPTION){
				 mark = 1; // "X"
			 } else {
				 mark = 1; // "X" also
			 }
		 return mark;
		 }
	 public int getTurnChoice()
			 {
				 int choice = 0;
				 int choiceFirst = JOptionPane.showConfirmDialog(null, "Would you like to go first?", "Choose Wisely", 0);
				 if (choiceFirst == JOptionPane.YES_OPTION){
						 choice = 1; // Player goes first
				 } else  if(choiceFirst == JOptionPane.NO_OPTION){
						 choice = 2; // Computer goes first
				 }
			return choice;
			}

		 public void playAgain()
		 {
			 Object[] options = { "ADMIT DEFEAT",
														"PLAY AGAIN"};
			 int playAgain = JOptionPane.showOptionDialog(null,
																		 "Choose your poison",
																		 "If at first you don't succeed...",
																		 JOptionPane.YES_NO_OPTION,
																		 JOptionPane.INFORMATION_MESSAGE,
																		 null,
																		 options,
																		 options[1]);
			 if(playAgain == JOptionPane.NO_OPTION){
				 resetGame(); // "Play again": reset game
				 } else {
				 System.exit(0); // "Admit defeat": exit program
				 }
		 }

		 /**

		 ** Game 1 ** initializeGame1
		 Player 1: Human, goes first, plays "X"
		 Player 2: Computer, goes second, plays "O"

		 ** Game 2 ** initializeGame2
		 Player 1: Human, goes first, plays "O"
		 Player 2: Computer, goes second, plays "X"

		 ** Game 3 ** initializeGame3 (Not yet functional)
		 Player 2: Human, goes second, plays "X"
		 Player 1: Computer, goes first, plays "O"

		 ** Game 4 ** initializeGame4 (Not yet functional)
		 Player 2: Human, goes second, plays "O"
		 Player 1: Computer, goes first, plays "X"

		 */

  /** GAME TYPE 1 */
 	private void initializeGame1()
 	{
 		// Set up the grid
       frame = new JFrame();
 		  frame.setBounds(100, 100, 600, 600);
 	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	    frame.setTitle("Tic Tac Toe: Human vs The Machine (featuring: Borg Collective)");
 		JPanel panel1 = new JPanel();
 	    panel1.setSize(300,300);
      panel1.setBorder(new LineBorder(new Color(153, 0, 255), 8));
 	    panel1.setLayout(new GridLayout(3,3));
 	    btnA1 = makeButton("A1");
 	    btnA2 = makeButton("A2");
 	    btnA3 = makeButton("A3");
 	    btnB1 = makeButton("B1");
 	    btnB2 = makeButton("B2");
 	    btnB3 = makeButton("B3");
 	    btnC1 = makeButton("C1");
 	    btnC2 = makeButton("C2");
 	    btnC3 = makeButton("C3");
 		panel1.add(btnA1);
 		panel1.add(btnA2);
 		panel1.add(btnA3);
 		panel1.add(btnB1);
 		panel1.add(btnB2);
 		panel1.add(btnB3);
 		panel1.add(btnC1);
 		panel1.add(btnC2);
 		panel1.add(btnC3);
 	    frame.add(panel1);
 	    frame.setVisible(true);

 		// Start the game
 		gameBoard= new GameBoard();

 	}
  /**
	 * Create the buttons with size and other preferences
	 */

  private JButton makeButton(String square)
	{
		JButton btn = new JButton();
		btn.setPreferredSize(new Dimension(175, 175));
		Font f = new Font("Tahoma", Font.PLAIN, 90);
		btn.setFont(f);
		btn.setForeground(Color.blue);
		btn.addActionListener(event -> btnClick(event, square));
		return btn;
	}

  private void btnClick(ActionEvent event, String square)
	{
		if(gameBoard.getSquare(square) != 0)
			return;

		JButton btn = (JButton)event.getSource();
		btn.setText("X");

		gameBoard.goPlay(square, 1);

		if(gameBoard.gameOver() == 3)
		{
			JOptionPane.showMessageDialog(null, "When universes collide, there are no victors", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			playAgain();
			return;
		}

		if(gameBoard.gameOver() == 1)
		{
			JOptionPane.showMessageDialog(null, "You are smarter than the computer. You Win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			playAgain();
			return;
		}

    String computerMove = gameBoard.getNextMove();
		gameBoard.goPlay(computerMove, 2);

		switch(computerMove)
		{
		case "A1":
			btnA1.setText("O");
			break;
		case "A2":
			btnA2.setText("O");
			break;
		case "A3":
			btnA3.setText("O");
			break;
		case "B1":
			btnB1.setText("O");
			break;
		case "B2":
			btnB2.setText("O");
			break;
		case "B3":
			btnB3.setText("O");
			break;
		case "C1":
			btnC1.setText("O");
			break;
		case "C2":
			btnC2.setText("O");
			break;
		case "C3":
			btnC3.setText("O");
			break;
		}
		if(gameBoard.gameOver() == 2)
		{
			JOptionPane.showMessageDialog(null, "Resistance is futile. Borg win.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			playAgain();
			return;
		}
	}

	/** GAME TYPE 2 */
	private void initializeGame2()
	{
		// Set up the grid
			 frame = new JFrame();
			frame.setBounds(100, 100, 600, 600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Tic Tac Toe: Human vs The Machine (featuring: Borg Collective)");
		JPanel panel1 = new JPanel();
			panel1.setSize(300,300);
			panel1.setBorder(new LineBorder(new Color(153, 0, 255), 8));
			panel1.setLayout(new GridLayout(3,3));
			btnA1 = makeButton2("A1");
			btnA2 = makeButton2("A2");
			btnA3 = makeButton2("A3");
			btnB1 = makeButton2("B1");
			btnB2 = makeButton2("B2");
			btnB3 = makeButton2("B3");
			btnC1 = makeButton2("C1");
			btnC2 = makeButton2("C2");
			btnC3 = makeButton2("C3");
		panel1.add(btnA1);
		panel1.add(btnA2);
		panel1.add(btnA3);
		panel1.add(btnB1);
		panel1.add(btnB2);
		panel1.add(btnB3);
		panel1.add(btnC1);
		panel1.add(btnC2);
		panel1.add(btnC3);
			frame.add(panel1);
			frame.setVisible(true);

		// Start the game
		gameBoard= new GameBoard();

	}
	/**
	 * Create the buttons with size and other preferences
	 */

	private JButton makeButton2(String square)
	{
		JButton btn = new JButton();
		btn.setPreferredSize(new Dimension(175, 175));
		Font f = new Font("Tahoma", Font.PLAIN, 90);
		btn.setFont(f);
		btn.setForeground(Color.green);
		btn.addActionListener(event -> btnClick2(event, square));
		return btn;
	}

	private void btnClick2(ActionEvent event, String square)
	{
		if(gameBoard.getSquare(square) != 0)
			return;

		JButton btn = (JButton)event.getSource();
		btn.setText("O");

		gameBoard.goPlay(square, 1);

		if(gameBoard.gameOver() == 3)
		{
			JOptionPane.showMessageDialog(null, "When universes collide, there are no victors", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			playAgain();
			return;
		}

		if(gameBoard.gameOver() == 1)
		{
			JOptionPane.showMessageDialog(null, "You are smarter than the computer. You Win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			playAgain();
			return;
		}

		String computerMove = gameBoard.getNextMove();
		gameBoard.goPlay(computerMove, 2);

		switch(computerMove)
		{
		case "A1":
			btnA1.setText("X");
			break;
		case "A2":
			btnA2.setText("X");
			break;
		case "A3":
			btnA3.setText("X");
			break;
		case "B1":
			btnB1.setText("X");
			break;
		case "B2":
			btnB2.setText("X");
			break;
		case "B3":
			btnB3.setText("X");
			break;
		case "C1":
			btnC1.setText("X");
			break;
		case "C2":
			btnC2.setText("X");
			break;
		case "C3":
			btnC3.setText("X");
			break;
		}
		if(gameBoard.gameOver() == 2)
		{
			JOptionPane.showMessageDialog(null, "Resistance is futile. Borg win.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			playAgain();
			return;
		}
	}

	private void resetGame()
	{
		gameBoard.reset();
		btnA1.setText("");
		btnA2.setText("");
		btnA3.setText("");
		btnB1.setText("");
		btnB2.setText("");
		btnB3.setText("");
		btnC1.setText("");
		btnC2.setText("");
		btnC3.setText("");
	}
}
