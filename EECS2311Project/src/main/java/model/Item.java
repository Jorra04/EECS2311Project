package model;

public class Item {
	public static int uid = 0; //keep track of each item created, qualitative or quantitative
	
	public int id;
	public String text;
	/* 
	 * possible fields in the future:
	 * dates
	 *
	 */
	
	public Item(String text) {
		this.id	 = getUID(); //unique to all items
		this.text = text;
	}
	
	
	public int getUID() {
		//TODO: if we save items, we need to save highest uid and load it here before assigning UIDs to more items.
		uid += 1;
		return uid;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
}
