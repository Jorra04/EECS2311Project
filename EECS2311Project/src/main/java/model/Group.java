package model;

import java.util.TreeMap;

public class Group {
	TreeMap<Integer, Item> items; // Id, Item
	String title;
	public Group(String title) {
		this.title = title;
		this.items = new TreeMap<Integer, Item>(); // Id, Item
		
	}
	
	//TODO: if an item is modified, also modify it in the group.
	
	public void insertItem(Item item) {
		items.put(Integer.valueOf(item.getUID()), item);
	}
	
	public void removeItem(Item item) {
		items.remove(Integer.valueOf(item.getUID()));
	}
	
	public int getTitle() {
		return this.getTitle();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
