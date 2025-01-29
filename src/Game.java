import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Game {

	public static enigma.console.Console cn = Enigma.getConsole("Helix Snake", 150, 26, 20, 0);
	public KeyListener klis; 

	public int keypr;   // key pressed?
	public int rkey;    // key   (for press/release)
	// ----------------------------------------------------

	public static Screen screen = new Screen();
	Scanner scn = new Scanner(System.in);
	MultiLinkedList aminoacids = new MultiLinkedList();
	private TextAttributes white = new TextAttributes(Color.WHITE, Color.GRAY);
	private TextAttributes red = new TextAttributes(Color.RED, Color.RED);
	private TextAttributes whiteBlack = new TextAttributes(Color.WHITE, Color.BLACK);
	private TextAttributes redBlack = new TextAttributes(Color.RED, Color.BLACK);
	private TextAttributes orangeBlack = new TextAttributes(Color.ORANGE, Color.BLACK);
	private TextAttributes yellowBlack = new TextAttributes(Color.YELLOW, Color.BLACK);
	private TextAttributes greenBlack = new TextAttributes(Color.GREEN, Color.BLACK);
	private TextAttributes cyanBlack = new TextAttributes(Color.CYAN, Color.BLACK);
	private TextAttributes blueBlack = new TextAttributes(Color.BLUE, Color.BLACK);
	private TextAttributes magentaBlack = new TextAttributes(Color.MAGENTA, Color.BLACK);

	public void run(int difficulty) throws Exception {

		while (true) {
			// Generating snake
			Snake snake = new Snake();

			klis = new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {
					if(keypr==0) {
						keypr=1;
						rkey=e.getKeyCode();
					}
				}
				public void keyReleased(KeyEvent e) {}
			};
			cn.getTextWindow().addKeyListener(klis);
			// ----------------------------------------------------

			DoubleLinkedList highscore = new DoubleLinkedList();
			// Reading highscore file and adding to double linked list
			try {
				FileReader fileReader = new FileReader("highscore.txt");
				BufferedReader br = new BufferedReader(fileReader);
				String line;
				while ((line = br.readLine()) != null){
					String[] data = line.split(";");
					highscore.add(Integer.parseInt(data[1]), data[0]);
				}
				br.close();
			}
			catch(FileNotFoundException error) {}
			catch(NullPointerException error) {}
			// Reading aminoacids file and adding to multi linked list

			try {
				FileReader fileReader = new FileReader("aminoacids.txt");
				BufferedReader br = new BufferedReader(fileReader);
				String st;
				String [] list;
				while ((st = br.readLine()) != null) {
					list = st.split(",");
					aminoacids.addCategory(list[0]);
					for (int i = 2; i < list.length; i++) {
						aminoacids.addItem(list[0], list[i]);
					}
				}
				br.close();
			}catch(FileNotFoundException error) {}

			int level = 0, time = 1;

			// Starting direction is left
			char direction = 'l';
			int round = 0;
			screen.generateScreen();

			// Getting random starting position
			int px = snake.getStartX(), py = snake.getStartY();

			// Game is starting 

			while(true) {
				// is Snake Grow control
				snake.isGrow(px,py);
				// Printing screen and snake at every step
				setCursorPosition(0, 0);
				screen.printScreen();
				snake.printing();

				// Arrow Key Controls
				if(keypr == 1) {    // if keyboard button pressed
					if(rkey == KeyEvent.VK_LEFT) {
						if(direction == 'u') {
							px--;   
							direction = 'l';
						}
						else if(direction == 'd') {
							px++;   
							direction = 'r';
						}
						else if(direction == 'l') {
							py++;   
							direction = 'd';
						}
						else if(direction == 'r') {
							py--;   
							direction = 'u';
						}
					}
					else if(rkey == KeyEvent.VK_RIGHT) {
						if(direction == 'u') {
							px++;
							direction = 'r';
						}
						else if(direction == 'd') {
							px--;   
							direction = 'l';
						}
						else if(direction == 'l') {
							py--;   
							direction = 'u';
						}
						else if(direction == 'r') {
							py++;   
							direction = 'd';
						}
					}
					// This case running only in Expert Mode. If player used snake's tongue
					else if (rkey == KeyEvent.VK_SPACE && difficulty == 75) {
						expertMode(px,py,direction);
						//up
						if(direction == 'u') {
							py--;   
						}
						//down
						else if(direction == 'd') {
							py++;   
						}
						//left
						else if(direction == 'l') {
							px--;   
						}
						//right
						else if(direction == 'r') {
							px++;   
						}
						keypr = 0;    // last action  
					}
					else if (rkey == KeyEvent.VK_P) {
						keypr = 0;
						setCursorPosition(24, 10);
						System.out.println("GAME PAUSED");
						while (true) {
							if (keypr == 1 && rkey == KeyEvent.VK_P)
								break;
							Thread.sleep(5);
						}
						keypr = 0;
						//up
						if(direction == 'u') {
							py--;   
						}
						//down
						else if(direction == 'd') {
							py++;   
						}
						//left
						else if(direction == 'l' ) {
							px--;   
						}
						//right
						else if(direction == 'r' ) {
							px++;   
						}
					}
					else {
						//up
						if(direction == 'u') {
							py--;   
						}
						//down
						else if(direction == 'd') {
							py++;   
						}
						//left
						else if(direction == 'l' ) {
							px--;   
						}
						//right
						else if(direction == 'r' ) {
							px++;   
						}
					}
					keypr = 0;    // last action  
				}
				//up
				else if(direction == 'u') {
					py--;   
				}
				//down
				else if(direction == 'd') {
					py++;   
				}
				//left
				else if(direction == 'l') {
					px--;   
				}
				//right
				else if(direction == 'r') {
					px++;   
				}
				// Portal mode
				if(difficulty == 75) {
					if(px == 0 && direction == 'l') {
						px = 58;
					}
					else if(px == 59 && direction == 'r') {
						px = 1;
					}
					else if(py == 0 && direction == 'u') {
						py = 23;
					}
					else if(py == 24 && direction == 'd') {
						py = 1;
					}	
				}

				snake.movement(px, py);

				// CRASH CONTROLS

				if(difficulty != 75 && snake.isCrashedToWall(px,py)) {
					setCursorPosition(83, 3);
					System.out.println("CRASHED TO WALL");

					if (highscore.isInHighScore(snake.getScore())) {

						setCursorPosition(83, 5);
						System.out.print("Please enter your name : ");
						String name = scn.nextLine();

						boolean flag = false;

						for (int i = 0; i < name.length(); i++)
							if (Character.isLetter(name.charAt(i)))
								flag = true;

						if (!flag)
							name = "(invalid name)";
						
						if (difficulty == 500)
							name = name + " (NORMAL MODE)";
						else if (difficulty == 200)
							name = name + " (HARD MODE)";
						else
							name = name + " (YILAN SERDAR MODE)";
						
						highscore.add(snake.getScore(), name);
						highscore.displayOnScreen();
					}
					else {
						setCursorPosition(83, 5);
						System.out.print("You couldn't get in the highscore table.");
					}
					break;
				}
				else if(snake.isCrashedToCell(px,py,difficulty)) {

					setCursorPosition(83, 3);
					System.out.println("CRASHED TO CELL");

					if (highscore.isInHighScore(snake.getScore())) {

						setCursorPosition(83, 5);
						System.out.print("Please enter your name : ");
						String name = scn.nextLine();

						boolean flag = false;

						for (int i = 0; i < name.length(); i++)
							if (Character.isLetter(name.charAt(i)))
								flag = true;

						if (!flag)
							name = "(invalid name)";
						
						if (difficulty == 500)
							name = name + " (NORMAL MODE)";
						else if (difficulty == 200)
							name = name + " (HARD MODE)";
						else
							name = name + " (YILAN SERDAR MODE)";

						highscore.add(snake.getScore(), name);
						highscore.displayOnScreen();
					}
					else {
						setCursorPosition(83, 5);
						System.out.print("You couldn't get in the highscore table.");
					}
					break;
				}
				else if(snake.isCrashedItself(px,py)) {

					setCursorPosition(83, 3);
					System.out.println("CRASHED TO YOURSELF");

					if (highscore.isInHighScore(snake.getScore())) {

						setCursorPosition(83, 5);
						System.out.print("Please enter your name : ");
						String name = scn.nextLine();

						boolean flag = false;

						for (int i = 0; i < name.length(); i++)
							if (Character.isLetter(name.charAt(i)))
								flag = true;

						if (!flag)
							name = "(invalid name)";
						
						if (difficulty == 500)
							name = name + " (NORMAL MODE)";
						else if (difficulty == 200)
							name = name + " (HARD MODE)";
						else
							name = name + " (YILAN SERDAR MODE)";

						highscore.add(snake.getScore(), name);
						highscore.displayOnScreen();
					}
					else {
						setCursorPosition(83, 5);
						System.out.print("You couldn't get in the highscore table.");
					}
					break;
				}
				// setting delay
				if(direction == 'r' || direction == 'l')
					Thread.sleep(difficulty);
				else if(direction == 'u' || direction == 'd')
					Thread.sleep(difficulty*(45/25));

				round++;

				if(difficulty == 75 && round % 35 == 0) {
					screen.addRandomWall();
					level++; 
				}
				else if(round % 40 == 0) {
					screen.addRandomWall();
					level++; 
				}

				if(snake.getSnake().size() % 3 == 0)
					snake.codonScoring(aminoacids, snake.getSnake().size());
				
				Game.setCursorPosition(62, 21);
				time = (round * difficulty) / 1000;
				Game.cn.setTextAttributes(whiteBlack);
				System.out.println("Time : " + time);
				Game.setCursorPosition(62, 22);
				System.out.println("Level : " + level);
			}

			// After the game highscore table is saving to text file.
			highscore.returnForWriting();

			setCursorPosition(83, 20);
			System.out.print("Please enter 'Y' or 'y' to play again");
			setCursorPosition(83, 21);
			System.out.print("or enter anything else to quit : ");
			String answer = scn.nextLine();
			if (!answer.equalsIgnoreCase("y")) {
				cleaner();
				cn.getTextWindow().setCursorPosition(20, 10);
				System.out.println("SEE YOU LATER");
				scn.close();
				Thread.sleep(2000);
				System.exit(0);
			}
			else {
				cleaner();
				menu();
			}
		}
	}
	
	public void cleaner() {

		for (int i = 0; i < 25; i++) {
			for(int j = 0; j < 150; j++) {
				cn.getTextWindow().setCursorPosition(j, i);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static void setCursorPosition(int x, int y) {
		cn.getTextWindow().setCursorPosition(x, y);
	}
	
	public MultiLinkedList getAminoacids() {
		return aminoacids;
	}
	
	public void menu() throws Exception {
		int a = 0;
		while(a < 5) {
			cn.getTextWindow().setCursorPosition(0, 0);
			System.out.print("\r\n");
			Game.cn.setTextAttributes(redBlack);

			cn.getTextWindow().setCursorPosition(0, 0);
			System.out.println("");

			System.out.print(" ___  ___  _______   ___       ___     ___    ___      ________  ________   ________  ___  __    _______      \r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 1);
			System.out.println("");

			Game.cn.setTextAttributes(orangeBlack);		
			System.out.print("|\\  \\|\\  \\|\\  ___ \\ |\\  \\     |\\  \\   |\\  \\  /  /|    |\\   ____\\|\\   ___  \\|\\   __  \\|\\  \\|\\  \\ |\\  ___ \\     \r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 2);
			System.out.println("");

			Game.cn.setTextAttributes(yellowBlack);
			System.out.print("\\ \\  \\\\\\  \\ \\   __/|\\ \\  \\    \\ \\  \\  \\ \\  \\/  / /    \\ \\  \\___|\\ \\  \\\\ \\  \\ \\  \\|\\  \\ \\  \\/  /|\\ \\   __/|    \r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 3);
			System.out.println("");

			Game.cn.setTextAttributes(greenBlack);
			System.out.print(" \\ \\   __  \\ \\  \\_|/_\\ \\  \\    \\ \\  \\  \\ \\    / /      \\ \\_____  \\ \\  \\\\ \\  \\ \\   __  \\ \\   ___  \\ \\  \\_|/__  \r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 4);
			System.out.println("");

			Game.cn.setTextAttributes(cyanBlack);
			System.out.print("  \\ \\  \\ \\  \\ \\  \\_|\\ \\ \\  \\____\\ \\  \\  /     \\/        \\|____|\\  \\ \\  \\\\ \\  \\ \\  \\ \\  \\ \\  \\\\ \\  \\ \\  \\_|\\ \\ \r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 5);
			System.out.println("");

			Game.cn.setTextAttributes(blueBlack);
			System.out.print("   \\ \\__\\ \\__\\ \\_______\\ \\_______\\ \\__\\/  /\\   \\          ____\\_\\  \\ \\__\\\\ \\__\\ \\__\\ \\__\\ \\__\\\\ \\__\\ \\_______\\\r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 6);
			System.out.println("");

			Game.cn.setTextAttributes(magentaBlack);
			System.out.print("    \\|__|\\|__|\\|_______|\\|_______|\\|__/__/ /\\ __\\        |\\_________\\|__| \\|__|\\|__|\\|__|\\|__| \\|__|\\|_______|\r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 7);
			System.out.println("");

			System.out.print("                                      |__|/ \\|__|        \\|_________|                                         \r\n");
			Thread.sleep(20);
			cn.getTextWindow().setCursorPosition(0, 8);
			System.out.println("");

			System.out.print("                                                                                                              \r\n");

			System.out.print( "                                                                                                              \r\n");

			System.out.println( "");
			if(a < 4) {
				cn.getTextWindow().setCursorPosition(0, 0);
				Game.cn.setTextAttributes(whiteBlack);
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
				System.out.println("                                                                                                                 ");
			}
			a++;
		}
		Game.cn.setTextAttributes(greenBlack);

		System.out.println("1-PLAY GAME");
		System.out.println("2-GAME RULES");
		System.out.println("3-HIGH SCORE TABLE");
		System.out.println("4-QUIT");
		System.out.print("Please enter your choice : ");
		String input = scn.nextLine();
		while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
			System.out.println("Unknown character input!");
			System.out.println("Please enter '1' or '2' or '3' or '4'");
			System.out.print("Please enter your choice : ");
			input = scn.nextLine();
		}
		if (input.equals("1")) {
			cleaner();
			cn.getTextWindow().setCursorPosition(0, 0);
			System.out.println("1-NORMAL MODE");
			System.out.println("2-HARD MODE");
			System.out.println("3-YILAN SERDAR MODE");
			System.out.println("4-GO BACK");
			System.out.print("Please enter your choice : ");
			input = scn.nextLine();
			while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
				System.out.println("Unknown character input!");
				System.out.println("Please enter '1' or '2' or '3' or '4'");
				System.out.print("Please enter your choice : ");
				input = scn.nextLine();
			}
			cleaner();
			if (input.equals("1"))
				run(500);
			else if (input.equals("2"))
				run(200);
			else if (input.equals("3"))
				run(75);
			else if (input.equals("4")) {
				cleaner();
				menu();
			}
		}
		else if (input.equals("2")) {
			cleaner();
			cn.getTextWindow().setCursorPosition(58, 0);
			System.out.println("----------GAME  RULES----------");
			System.out.println();
			System.out.println(
					"-There will be 3 letters randomly generated in the game area at the start. When the snake eats a letter, a new letter must be generated in the game area to maintain starting number of letters.\r\n"
							+ "-The snake will be moved by the player using arrow keys (only right and left). Every move take half of a second.\r\n"
							+ "-Press 'P' button to pause the game and continue the game.\r\n"
							+ "-When the snake eats a letter, the letter will be added to the front (head) of the snake.\r\n"
							+ "-When the snake eats a letter, the player earns 5 points. The player will also earn extra points when the snake completes an amino-acid codon that was given in the input file.\r\n"
							+ "-For YILAN SERDAR MODE : Use space button to expand the snake's tongue and destroy anything on your way. You can pass through the walls (not cells) as if the map is spherical.\r\n"
							+ "");
			System.out.println("---------- PLEASE PRESS 'ENTER' TO RETURN TO THE MENU ----------");
			scn.nextLine();
			cleaner();
			menu();
		}
		else if (input.equals("3")) {
			
			DoubleLinkedList highscore = new DoubleLinkedList();
			try {
				FileReader fileReader = new FileReader("highscore.txt");
				BufferedReader br = new BufferedReader(fileReader);
				String line;
				while ((line = br.readLine()) != null){
					String[] data = line.split(";");
					highscore.add(Integer.parseInt(data[1]), data[0]);
				}
				br.close();
			}
			catch(FileNotFoundException error) {}
			catch(NullPointerException error) {}

			cleaner();
			cn.getTextWindow().setCursorPosition(6, 0);
			System.out.println("-------------- HIGHSCORE TABLE --------------");
			highscore.displayHighScoreAtMenu();

			System.out.println("\n---------- PLEASE PRESS 'ENTER' TO RETURN TO THE MENU ----------");
			scn.nextLine();
			cleaner();
			menu();
		}
		else {
			cleaner();
			cn.getTextWindow().setCursorPosition(20, 10);
			System.out.println("SEE YOU LATER");
			scn.close();
			Thread.sleep(2000);
			System.exit(0);
		}
	}
	
	public void expertMode(int px, int py, char direction) throws InterruptedException {
		int x = px, y = py, counter = 0;
		Game.cn.setTextAttributes(red);
		if(direction == 'u') {
			while(y > 1 && counter < 5) {
				y--;
				counter++;
				if(!(screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {

					cn.getTextWindow().setCursorPosition(x, y);
					System.out.print(" ");
					Thread.sleep(5);

				}
				else if((screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {
					screen.getScreen()[y][x] = ' ';
					screen.addRandomLetter();
					break;
				}
				if(screen.getScreen()[y][x] == '#') {
					screen.getScreen()[y][x] = ' ';
					break;
				}
			}
		}
		//down
		else if(direction == 'd') {
			while(y < 23 && counter < 5) {
				y++;
				counter++;
				if(!(screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {

					Thread.sleep(5);
					cn.getTextWindow().setCursorPosition(x, y);
					System.out.print(" ");
				}
				else if((screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {
					screen.getScreen()[y][x] = ' ';
					screen.addRandomLetter();
					break;
				}
				if(screen.getScreen()[y][x] == '#') {
					screen.getScreen()[y][x] = ' ';
					break;
				}

			}
		}
		//left
		else if(direction == 'l') {
			while(x > 1 && counter < 12) {
				x--;
				counter++;
				if(!(screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {

					Thread.sleep(5);
					cn.getTextWindow().setCursorPosition(x, y);
					System.out.print(" ");
				}
				else if((screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {
					screen.getScreen()[y][x] = ' ';
					screen.addRandomLetter();
					break;
				}
				if(screen.getScreen()[y][x] == '#') {
					screen.getScreen()[y][x] = ' ';
					break;
				}

			}

		}
		//right
		else if(direction == 'r') {
			while(x < 58 && counter < 12) {
				x++;
				counter++;
				if(!(screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {

					Thread.sleep(5);
					cn.getTextWindow().setCursorPosition(x, y);
					System.out.print(" ");
				}
				else if((screen.getScreen()[y][x] == 'A' || screen.getScreen()[y][x] == 'G' || screen.getScreen()[y][x] == 'T' || screen.getScreen()[y][x] == 'C')) {
					screen.getScreen()[y][x] = ' ';
					screen.addRandomLetter();
					break;
				}
				if(screen.getScreen()[y][x] == '#') {
					screen.getScreen()[y][x] = ' ';
					break;
				}
			}
		}
		Game.cn.setTextAttributes(white);
	}
}