package fr.becpg.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <p>Pagination class.</p>
 *
 * @author matthieu
 */
@JsonInclude(Include.NON_NULL)
public class Pagination {

	private boolean hasMoreItems;
	private int count;
	private Integer totalItems;

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

	/**
	 * <p>Getter for the field <code>totalItems</code>.</p>
	 *
	 * @return the number of entities matching the request as a whole, null when the repository does not know it
	 *         or does not publish it (remote API &lt; 4.4)
	 */
	public Integer getTotalItems() {
		return totalItems;
	}

	/**
	 * <p>Setter for the field <code>totalItems</code>.</p>
	 *
	 * @param totalItems the number of entities matching the request as a whole, null when unknown
	 */
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Pagination{" + "hasMoreItems=" + hasMoreItems + ", count=" + count + ", totalItems=" + totalItems + '}';
	}

}
