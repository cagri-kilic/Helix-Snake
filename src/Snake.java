import java.awt.Color;
import java.util.Random;
import enigma.console.TextAttributes;

public class Snake {
	private SingleLinkedList snake = new SingleLinkedList();
	private SingleLinkedList letters = new SingleLinkedList();

	private int score = 0;
	private int lastCheckedLetterPos = 1;
	private TextAttributes green = new TextAttributes(Color.BLACK, Color.GREEN);
	private TextAttributes yellow = new TextAttributes(Color.BLACK, Color.YELLOW);
	private TextAttributes white = new TextAttributes(Color.WHITE, Color.BLACK);
	private Screen scr = new Screen();
	private int scoreTableY = 3;
	private int scoreTableX = 64;
	private int startX, startY;

	Random random = new Random();

	public Snake() {

		int [] coordinate = new int [2];
		int x = random.nextInt(10) + 30;
		int y = random.nextInt(5) + 10;
		startX = x;
		startY = y;
		int rnd = random.nextInt(4);
		char letter = ' ';
		if(rnd == 0) {
			letter = 'A';
		}
		else if(rnd == 1) {	
			letter = 'C';
		}
		else if(rnd == 2) {
			letter = 'G';
		}
		else if(rnd == 3) {
			letter = 'T';
		}
		coordinate[0] = x;
		coordinate[1] = y;
		snake.add(coordinate);
		this.letters.addToEnd(letter);

		//
		rnd = random.nextInt(4);
		if(rnd == 0) {
			letter = 'A';
		}
		else if(rnd == 1) {	
			letter = 'C';
		}
		else if(rnd == 2) {
			letter = 'G';
		}
		else if(rnd == 3) {
			letter = 'T';
		}
		coordinate[0] = x + 1;
		coordinate[1] = y;
		snake.add(coordinate);
		this.letters.addToEnd(letter);
		//
		rnd = random.nextInt(4);
		if(rnd == 0) {
			letter = 'A';
		}
		else if(rnd == 1) {	
			letter = 'C';
		}
		else if(rnd == 2) {
			letter = 'G';
		}
		else if(rnd == 3) {
			letter = 'T';
		}
		coordinate[0] = x + 2;
		coordinate[1] = y;
		snake.add(coordinate);
		this.letters.addToEnd(letter);
		startX = x + 2;
		startY = y;
	}

	public void movement(int x, int y) {
		int [] currentPosition = new int [2];
		// Getting snake's head and moving forward
		currentPosition[0] = x; 
		currentPosition[1] = y; 
		snake.add(currentPosition);
		snake.deleteAtPos(1);	
	}
	
	public void isGrow(int x, int y) {

		if(Game.screen.getScreen()[y][x] != '\u0000') {
			if(Game.screen.getScreen()[y][x] == 'A' || Game.screen.getScreen()[y][x] == 'C' || Game.screen.getScreen()[y][x] == 'G' || Game.screen.getScreen()[y][x] == 'T') {
				grow(x, y, Game.screen.getScreen()[y][x]);
				Game.screen.getScreen()[y][x] = ' ';
				scr.addRandomLetter();
			}	
		}
	}

	public void grow(int x, int y, char letter) {
		int [] coordinate = new int [2];
		coordinate[0] = x;
		coordinate[1] = y;
		snake.add(coordinate);
		this.letters.addToEnd(letter);
		score += 5;
		Game.setCursorPosition(62, 1);
		System.out.print("SCORE: " + score);
		Game.setCursorPosition(62, 2);
		System.out.println("----------");
	}

	public boolean isCrashedToWall(int x, int y) {
		// If its crashed to wall
		if((0 < x && x < 59) && (0 < y && y < 24))
			return false;

		return true;
	}

	public boolean isCrashedToCell(int x, int y, int difficulty) {
		// If its crashed to wall in the area
		if(difficulty == 75 && (0 == x || x == 59) || (0 == y || y == 24) && (Game.screen.getScreen()[y][x] == '#'))
			return false;
		if((0 < x && x < 59) && (0 < y && y < 24) && !(Game.screen.getScreen()[y][x] == '#')) 
			return false;

		return true;
	}

	// If its crashed to its own body
	public boolean isCrashedItself(int x, int y){
		int currentPosition [] = new int [2];
		currentPosition[0] = x;
		currentPosition[1] = y;
		if(snake.searchHowMany(currentPosition) == 1)
			return false;
		return true;
	}
	// This function must be call every eat 3 times
	public void codonScoring(MultiLinkedList aminoacids, int size) {
		String temp = "";
		String data;
		int point;

		// Getting last 3 letter
		if(lastCheckedLetterPos - 1 != size) {

			for (int i = 0; i < 3; i++) {
				temp += letters.getDataAtPosition(lastCheckedLetterPos);
				lastCheckedLetterPos++;
			}
			
			// Comparing temp with codons
			data = aminoacids.search(temp);
			point = Integer.parseInt(data.substring(4));
			score += point;
			// Writing score to scoretable
			if(scoreTableY == 19)
				scoreTableX = 73;
			Game.cn.setTextAttributes(white);
			Game.setCursorPosition(scoreTableX, scoreTableY);
			System.out.println(data);
			scoreTableY++;
		}
	}
	
	public void printing() {
		int[] currentPosition;
		int x, y;
		scoreBoard();
		Game.cn.setTextAttributes(yellow);
		for (int i = snake.size(); i >= 1; i--) {
			// Getting snake's parts
			currentPosition = snake.getDataAtPos(i);
			x = currentPosition[0];  
			y = currentPosition[1];
			if ( i == snake.size() - 1 )
				Game.cn.setTextAttributes(green);
			Game.setCursorPosition(x, y);
			System.out.print(letters.getDataAtPosition(i));
		}
		Game.cn.setTextAttributes(white);
	}
	
	public void scoreBoard() {
		Game.setCursorPosition(62, 0);
		System.out.println("|-----------------|");
		Game.setCursorPosition(62, 1);
		System.out.print("| SCORE: " + score);
		Game.setCursorPosition(80, 1);
		System.out.println("|");
		Game.setCursorPosition(62, 2);
		System.out.println("|--------|--------|");
		for (int i = 0; i < 17; i++) {
			Game.setCursorPosition(62, 3 + i);
			System.out.println("|");
			Game.setCursorPosition(71, 3 + i);
			System.out.println("|");
			Game.setCursorPosition(80, 3 + i);
			System.out.println("|");
		}
		Game.setCursorPosition(62, 20);

		System.out.println("|--------|--------|");
	}

	public int getScore() {
		return score;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public SingleLinkedList getSnake() {
		return snake;
	}

	public void setSnake(SingleLinkedList snake) {
		this.snake = snake;
	}

	public SingleLinkedList getLetters() {
		return letters;
	}

	public void setLetters(SingleLinkedList letters) {
		this.letters = letters;
	}
}