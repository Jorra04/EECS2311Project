package model;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int id =0;
	public String text;
	/* 
	 * possible fields in the future:
	 * dates
	 *
	 */
	
	public Item(String text) {
		this.id	 = generateID(text); //unique to all items
		this.text = text;
		System.out.println("item created");
	}
	
	
	public int getID() {
		//TODO: if we save items, we need to save highest uid and load it here before assigning UIDs to more items.
		return id;
	}
	
	public int generateID(String s) {
		id = s.hashCode();
		return id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	

	//SOMEONE TOOK 2030 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
//		if (id != other.id)
//			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", text=" + text + "]";
	}
	
	public static void resetUID() {
		int x  = 0;
	}
	
}
