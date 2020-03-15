package model;

import java.util.HashMap;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class VennModel {
	ObservableSet<Item> setList;
	HashMap<String, Group> groupMap;
	public VennModel() {
//		this.itemList = new ArrayList<Item>();
//		this.groupList = new ArrayList<Group>();
		groupMap = new HashMap<String, Group>();
		setList = FXCollections.observableSet();
	}
	
	public ObservableSet<Item> getItemSet() {
		return setList;
	}
	
	public void addSet(Item item){
		setList.add(item);
	}
	
	public boolean contains(Item item) {
		return setList.contains(item);
	}
	
	public boolean containsText(String s) {
		for(Item i: setList) {
			if(i.text.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public void changeItemText(Item item) {
			setList.add(item);
		}
	
	public void changeItemText(String s) {
		for (Item item : setList) {
			if(item.text == s) {
				item.text = s;
			}
		}
	}
	
	public void setItemSet(ObservableSet<Item> itemSet) {
		this.setList = itemSet;
	}
	
	public void removeAll() {
		this.setList.clear();
	}
	
	public void createGroup(String title) {
		groupMap.put(title, new Group(title));
	}

	public HashMap<String, Group> getGroupMap() {
		return groupMap;
	}
	
	public int groupMapSize() {
		return groupMap.size();
	}
	
	public int size() {
		return setList.size();
	}

}