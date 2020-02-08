package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VennModel {
	ObservableList<Item> itemList;
	ObservableList<Group> groupList;
	public VennModel() {
//		this.itemList = new ArrayList<Item>();
//		this.groupList = new ArrayList<Group>();
		itemList = FXCollections.observableArrayList();
		groupList = FXCollections.observableArrayList();
	}
	
	public ObservableList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ObservableList<Item> itemList) {
		this.itemList = itemList;
	}

	public ObservableList<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(ObservableList<Group> groupList) {
		this.groupList = groupList;
	}
	
	public ObservableList<String> getStringItemList() {
		ObservableList<String> stringList = FXCollections.observableArrayList();
		for(Item item : itemList) {
			stringList.add(item.getText());
		}
		return stringList;
	}
	
}