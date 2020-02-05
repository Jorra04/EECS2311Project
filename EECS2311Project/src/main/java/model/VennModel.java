package model;

import java.util.ArrayList;
import java.util.List;

public class VennModel {
	List<Item> itemList;
	List<Group> groupList;
	public VennModel() {
		this.itemList = new ArrayList<Item>();
		this.groupList = new ArrayList<Group>();
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public List<Group> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public void addGroup(Group group) {
		this.groupList.add(group);
	}
	
	public void addItem(Item item) {
		this.itemList.add(item);
	}
	
}