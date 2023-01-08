package me.coley.cafedude.classfile.constant;

/**
 * Method type pool entry. Points to an UTF constant.
 *
 * @author Matt Coley
 */
public class CpMethodType extends ConstPoolEntry {
	private int index;

	/**
	 * @param index
	 * 		Index of method descriptor UTF in pool.
	 */
	public CpMethodType(int index) {
		super(METHOD_TYPE);
		this.index = index;
	}

	/**
	 * @return Index of method descriptor UTF in pool.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 * 		New index of method descriptor UTF in pool.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CpMethodType that = (CpMethodType) o;
		return index == that.index;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(index);
	}
}
