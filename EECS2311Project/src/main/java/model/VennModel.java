package model;

import java.util.HashMap;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VennModel {
	ObservableList<Item> itemList;
	HashMap<String, Group> groupMap;
	public VennModel() {
//		this.itemList = new ArrayList<Item>();
//		this.groupList = new ArrayList<Group>();
		itemList = FXCollections.observableArrayList();
		groupMap = new HashMap<String, Group>();
	}
	
	public ObservableList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ObservableList<Item> itemList) {
		this.itemList = itemList;
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

}