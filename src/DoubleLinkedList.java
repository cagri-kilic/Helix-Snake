import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DoubleLinkedList {
	private DLLNode head;
	private DLLNode tail;
	private int size;

	public DoubleLinkedList() {
		size = 0;
		head = null;
		tail = null;
	}

	public void add(int score, String name) {

		DLLNode newDLLNode = new DLLNode(score, name);

		if(size == 0) {
			head = newDLLNode;
			tail = newDLLNode;
		}
		else{

			if (score <= tail.getData() && size == 10)
				return;

			if (score > head.getData()) {
				newDLLNode.setNext(head);
				head = newDLLNode;
			}
			else if (score <= tail.getData()) {
				tail.setNext(newDLLNode);
				tail = newDLLNode;
			}
			else {
				DLLNode temp = head;

				while (temp.getNext().getData() >= score)
					temp = temp.getNext();

				newDLLNode.setPrev(temp);
				newDLLNode.setNext(temp.getNext());
				temp.getNext().setPrev(newDLLNode);
				temp.setNext(newDLLNode);
			}
		}
		size++;
	}

	public void displayOnScreen() {

		int y = 8;
		Game.setCursorPosition(83, 7);
		System.out.print("High Score Table :");

		DLLNode temp = head;
		int count = 1;

		while(temp != null && count <=10)	{
			Game.setCursorPosition(83, y);
			System.out.print(count + ". " + temp.getData2() + " - " + temp.getData() + " points\n");;
			temp = temp.getNext();
			count++;
			y++;
		}
	}
	public void displayHighScoreAtMenu() {

		int y = 2;
		Game.setCursorPosition(18, 2);
		System.out.print("High Score Table :");

		DLLNode temp = head;
		int count = 1;

		while(temp != null && count <=10)	{
			Game.setCursorPosition(18, y);
			System.out.print(count + ". " + temp.getData2() + " - " + temp.getData() + " points\n");;
			temp = temp.getNext();
			count++;
			y++;
		}
	}

	public void returnForWriting() throws FileNotFoundException {
		
		DLLNode temp = head;
		
		PrintWriter out = new PrintWriter("highscore.txt");

		while (temp != null) {
			out.println(temp.getData2() + ";" + temp.getData());
			temp = temp.getNext();
		}
		out.close();
	}

	public boolean isInHighScore(int score) {
		if (size < 10)
			return true;
		else if (score > tail.getData())
			return true;
		else
			return false;
	}
}
class DLLNode {

	private String data2;
	private int data;
	private DLLNode prev;
	private DLLNode next;

	public DLLNode(int data, String data2) {
		this.data = data;
		this.data2 = data2;
		this.prev = null;
		this.next = null;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public DLLNode getPrev() {
		return prev;
	}

	public void setPrev(DLLNode prev) {
		this.prev = prev;
	}

	public DLLNode getNext() {
		return next;
	}

	public void setNext(DLLNode next) {
		this.next = next;
	}
}