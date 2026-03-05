package fr.becpg.api.model;

/**
 * <p>Pagination class.</p>
 *
 * @author matthieu
 */
public class Pagination {

	private boolean hasMoreItems;
	private int count;

	/**
	 * <p>isHasMoreItems.</p>
	 *
	 * @return a boolean
	 */
	public boolean isHasMoreItems() {
		return hasMoreItems;
	}

	/**
	 * <p>Setter for the field <code>hasMoreItems</code>.</p>
	 *
	 * @param hasMoreItems a boolean
	 */
	public void setHasMoreItems(boolean hasMoreItems) {
		this.hasMoreItems = hasMoreItems;
	}

	/**
	 * <p>Getter for the field <code>count</code>.</p>
	 *
	 * @return a int
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <p>Setter for the field <code>count</code>.</p>
	 *
	 * @param count a int
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Pagination{" + "hasMoreItems=" + hasMoreItems + ", count=" + count + '}';
	}

}
