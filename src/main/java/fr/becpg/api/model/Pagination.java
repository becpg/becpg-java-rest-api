package fr.becpg.api.model;

public class Pagination {

	private boolean hasMoreItems;
	private int count;

	public boolean isHasMoreItems() {
		return hasMoreItems;
	}

	public void setHasMoreItems(boolean hasMoreItems) {
		this.hasMoreItems = hasMoreItems;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Pagination{" + "hasMoreItems=" + hasMoreItems + ", count=" + count + '}';
	}

}
