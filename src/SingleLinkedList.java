public class SingleLinkedList {
	
	private Node1 head;
	private Node start;
	private Node end;
	public int size;

	// Constructor
	public SingleLinkedList() {
		start = null;
		end = null;
		size = 0;
	}

	public void addToEnd(Object dataToAdd) {
		Node1 newNode = new Node1(dataToAdd);

		if (head == null)
			head = newNode;
		else {
			Node1 temp = head;
			while (temp.getLink() != null)
				temp = temp.getLink();

			temp.setLink(newNode);
		}
	}

	public Object getDataAtPosition(int pos) {
		Node1 temp = head;
		int count = 1;

		while (temp != null) {
			if (count == pos)
				return temp.getData();
			count++;
			temp = temp.getLink();
		}
		return null;
	}

	// Function to get size of list
	public int size() {
		return size;
	}

	// Function to insert an element at beginning
	public void insertAtStart(int[] val) {
		Node nptr = new Node(val, null);
		size++;
		if (start == null) {
			start = nptr;
			end = start;
		} else {
			nptr.setNext(start);
			start = nptr;
		}
	}

	public void insertAtEnd(int[] val) {
		Node nptr = new Node(val, null);
		size++;
		if (start == null) {
			start = nptr;
			end = start;
		}
		else {
			end.setNext(nptr);
			end = nptr;
		}
	}

	// Function to insert an element at end
	public void add(int[] coordinate) {
		Node nptr = new Node(coordinate, null);
		size++;
		if (start == null) {
			start = nptr;
			end = start;
		}
		else {
			end.setNext(nptr);
			end = nptr;
		}
	}

	// Function to delete an element at position
	public void deleteAtPos(int pos) {
		if (pos == 1) {
			start = start.getNext();
			size--;
			return;
		}
		if (pos == size) {
			Node s = start;
			Node t = start;
			while (s != end) {
				t = s;
				s = s.getNext();
			}
			end = t;
			end.setNext(null);
			size--;
			return;
		}
		Node ptr = start;
		pos = pos - 1;
		for (int i = 1; i < size - 1; i++) {
			if (i == pos) {
				Node tmp = ptr.getNext();
				tmp = tmp.getNext();
				ptr.setNext(tmp);
				break;
			}
			ptr = ptr.getNext();
		}
		size--;
	}

	public void deleteHead() {
		Node temp = start;
		start = temp.getNext();
	}

	// Function to display elements
	public String display() {
		if (size == 0) {
			return "";
		}
		String output = "";
		Node temp = start;
		while (temp != null) {
			output += temp.getData() + " ";
			temp = temp.getNext();
		}
		return output;
	}

	// Function to find position of data
	public int searchPos(int[] data) {
		Node temp = start;
		int count = 0;

		while (temp != null) {
			count++;
			if (data == temp.getData())
				return count;
			
			temp = temp.getNext();
		}
		return -1;
	}

	public int[] getDataAtPos(int pos) {
		Node temp = start;
		int count = 1;

		while (temp != null) {
			if (count == pos)
				return temp.getData();
			
			count++;
			temp = temp.getNext();
		}
		return null;
	}

	// Function to find how many data
	public int searchHowMany(int[] data) {
		Node temp = start;
		int count = 0;

		while (temp != null) {
			if (data[0] == temp.getData()[0] && data[1] == temp.getData()[1])
				count++;
			
			temp = temp.getNext();
		}
		return count;
	}

	// Function to find how many data

	public Node getHead() {
		return start;
	}
}

class Node {
	protected int[] data;
	protected Node next;

	// Constructor
	public Node() {
		next = null;
		data = null;
	}

	// Constructor
	public Node(int[] coordinate, Node n) {
		data = coordinate;
		next = n;
	}

	// Function to set link to next Node
	public void setNext(Node n) {
		next = n;
	}

	// Function to set data to current Node
	public void setData(int[] d) {
		data = d;
	}

	// Function to get link to next node
	public Node getNext() {
		return next;
	}

	// Function to get data from current Node
	public int[] getData() {
		return data;
	}
}

class Node1 {
	Object data;
	Node1 link;

	public Node1(Object dataToAdd) {
		data = dataToAdd;
		link = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Node1 getLink() {
		return link;
	}

	public void setLink(Node1 link) {
		this.link = link;
	}
}