import java.awt.Color;
import java.util.Random;
import enigma.console.TextAttributes;

public class Screen {
	
	public static char [][] screen = new char [25][60];
	private TextAttributes cyan = new TextAttributes(Color.BLACK, Color.GRAY);
	private TextAttributes gray = new TextAttributes(Color.GRAY, Color.GRAY);
	private TextAttributes white = new TextAttributes(Color.WHITE, Color.BLACK);
	private TextAttributes yellow = new TextAttributes(Color.YELLOW, Color.GRAY);
	
	public char[][] getScreen() {
		return Screen.screen;
	}

	public void setScreen(char[][] screen) {
		Screen.screen = screen;
	}

	public void generateScreen() {
		for (int i = 0; i < 60; i++) {
			Screen.screen[0][i] = '#';
			Screen.screen[24][i] = '#';
		}
		for (int i = 1; i < 24 ; i++) {
			for (int j = 1; j < 59; j++) {
				Screen.screen[i][j] = ' ';
			}
		}
		for (int i = 0; i < 24; i++) {
			Screen.screen[i][0] = '#';
			Screen.screen[i][59] = '#';
		}

		addRandomLetter();
		addRandomLetter();
		addRandomLetter();
	}
	
	public void printScreen() {
	
		for (int i = 0; i < screen.length; i++) {
			for (int j = 0; j < screen[i].length; j++) {
				if(Screen.screen[i][j] == '#')
					Game.cn.setTextAttributes(cyan);
				else if(Screen.screen[i][j] == ' ')
					Game.cn.setTextAttributes(gray);
				else
					Game.cn.setTextAttributes(yellow);

				System.out.print(Screen.screen[i][j]);
			}
			System.out.println("");
		}
		Game.cn.setTextAttributes(white);
	}
	
	public void addRandomLetter() {
		Random random = new Random();
		boolean flag = true;
		while(flag == true) {
			int rnd = random.nextInt(4);
			int x = random.nextInt(58) + 1;
			int y = random.nextInt(23) + 1;
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
			if(screen[y][x] == ' ') {
				Screen.screen[y][x] = letter;
				flag = false;
			}
		}
	}
	
	public void addRandomWall() {
		Random random = new Random();
		boolean flag = true;
		while(flag == true) {
			int x = random.nextInt(58) + 1;
			int y = random.nextInt(23) + 1;
			if(screen[y][x] == ' ') {
				Screen.screen[y][x] = '#';
				flag = false;
			}
		}
	}
}