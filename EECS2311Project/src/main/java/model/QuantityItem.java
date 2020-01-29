package model;

import model.Item;

public class QuantityItem extends Item{

	public int count; // amount of item
	
	public QuantityItem(String text, int count) {
		super(text);
		this.count = count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}
}
