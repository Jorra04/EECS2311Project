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
	
	public void incrementCount() {
		this.count++;
	}
	
	public int getCount() {
		return this.count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + count;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuantityItem other = (QuantityItem) obj;
		if (count != other.count)
			return false;
		return true;
	}
	
	
}
