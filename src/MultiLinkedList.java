public class MultiLinkedList {
	CategoryNode head;
	public void addCategory(String dataToAdd) {
		CategoryNode temp;
		if (head == null) {
			temp = new CategoryNode(dataToAdd);
			head = temp;
		}
		else {
			temp = head;
			while (temp.getDown() != null)
				temp = temp.getDown();
			CategoryNode newnode = new CategoryNode(dataToAdd);
			temp.setDown(newnode);
		}
	}
	public void addItem(String Category, String Item) {
		if (head == null)
			System.out.println("Add a Category before Item");
		else {
			CategoryNode temp = head;
			while (temp != null)
			{
				if (Category.equals(temp.getCategoryName())) {
					ItemNode temp2 = temp.getRight();
					if (temp2 == null) {
						temp2 = new ItemNode(Item);
						temp.setRight(temp2);
					}
					else {
						while (temp2.getNext() != null)
							temp2 = temp2.getNext();
						ItemNode newnode = new ItemNode(Item);

						temp2.setNext(newnode);
					}
				}
				temp = temp.getDown();
			}
		}
	}
	public int sizeCategory()
	{
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			CategoryNode temp = head;
			while (temp != null)
			{
				count++;
				temp=temp.getDown();
			}
		}
		return count;
	}
	public int sizeItem()
	{
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			CategoryNode temp = head;
			while (temp != null)
			{
				ItemNode temp2 = temp.getRight();
				while (temp2 != null)
				{
					count++;
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
			}
		}
		return count;
	}
	public void display()
	{
		if (head == null)
			System.out.println("linked list is empty");
		else {
			CategoryNode temp = head;
			while (temp != null)
			{
				System.out.print(temp.getCategoryName() + " --> ");
				ItemNode temp2 = temp.getRight();
				while (temp2 != null)
				{
					System.out.print(temp2.getItem() + " ");
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
				System.out.println();
			}
		}
	}
	public String search(String codon) {

		String output = "";
		CategoryNode temp = head;

		while(temp != null) {
			ItemNode temp2 = temp.getRight();

			while(temp2 != null) {
				if(codon.equals(temp2.getItem().substring(0,3)))
					output = temp2.getItem();
				temp2 = temp2.getNext();
			}
			temp = temp.getDown();
		}
		return output;
	}
}

class ItemNode {
	private String Item;
	private ItemNode next;
	public ItemNode(String dataToAdd) {
		Item = dataToAdd;
		next = null;
	}
	public String getItem() { return Item; }
	public void setItem(String data) { this.Item = data; }
	public ItemNode getNext() { return next; }
	public void setNext(ItemNode next) { this.next = next; }
}

class CategoryNode {
	private String CategoryName;
	private CategoryNode down;
	private ItemNode right;
	public CategoryNode(String dataToAdd) {
		CategoryName = dataToAdd;
		down = null;
		right = null;
	}
	public Object getCategoryName() { return CategoryName; }
	public void setCategoryName(String data) { this.CategoryName = data; }
	public CategoryNode getDown() { return down; }
	public void setDown(CategoryNode down) { this.down = down; }
	public ItemNode getRight() { return right; }
	public void setRight(ItemNode right) { this.right = right; }
}


