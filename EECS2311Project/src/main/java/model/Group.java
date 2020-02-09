package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Group {
	TreeMap<Integer, Item> items; // Id, Item
	String title;
	public Group(String title) {
		this.title = title;
		this.items = new TreeMap<Integer, Item>(); // Id, Item
		
	}
	
	public void insertItem(Item item) {
		items.put(Integer.valueOf(item.getID()), item);
	}
	
	public void insertItems(Collection<Item> items) {
		for(Item item: items) {
			System.out.println(item.toString());
			this.insertItem(item);
		}
	}
	
	public void removeItem(Item item) {
		items.remove(Integer.valueOf(item.getID()));
	}
	
	public void removeItems(Collection<Item> items) {
		for(Item item : items) {
			this.removeItem(item);
		}
	}
	public void removeAll() {
		this.items.clear();
	}
	
	public int getTitle() {
		return this.getTitle();
	}
	public boolean isEmpty() {
		return items.size() == 0;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toVisualList() {
		String visualString = "";
    	for(Map.Entry<Integer, Item> entry : items.entrySet()) {
    		System.out.println(entry.getValue().getText());
    		visualString = visualString + "ID: " + entry.getValue().getID() + " - " + entry.getValue().getText() + "\n";
    	}
    	return visualString;
    }
	
	public Set<Integer> findMatching(Group other) {
		Set<Integer> temp = new HashSet<Integer>(items.keySet());
		temp.retainAll(other.items.keySet());
		System.out.println(temp);
		return temp;
	}
	
}
