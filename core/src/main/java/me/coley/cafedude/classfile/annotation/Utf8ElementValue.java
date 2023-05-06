package me.coley.cafedude.classfile.annotation;

import me.coley.cafedude.classfile.constant.CpEntry;
import me.coley.cafedude.classfile.constant.CpUtf8;

import java.util.Collections;
import java.util.Set;

/**
 * UTF8 string element value.
 *
 * @author Matt Coley
 */
public class Utf8ElementValue extends ElementValue {
	private CpUtf8 value;

	/**
	 * @param tag
	 * 		ASCII tag representation, must be {@code s}.
	 * @param value
	 * 		Index of utf8 constant.
	 */
	public Utf8ElementValue(char tag, CpUtf8 value) {
		super(tag);
		if (tag != 's')
			throw new IllegalArgumentException("UTF8 element value must have 's' tag");
		this.value = value;
	}

	/**
	 * @return Index of utf8 constant.
	 */
	public CpUtf8 getValue() {
		return value;
	}

	/**
	 * @param utfIndex
	 * 		Index of utf8 constant.
	 */
	public void setValue(CpUtf8 value) {
		this.value = value;
	}

	/**
	 * @return ASCII tag representation of a string, {@code s}.
	 */
	@Override
	public char getTag() {
		return super.getTag();
	}

	@Override
	public Set<CpEntry> cpAccesses() {
		return Collections.singleton(value);
	}

	@Override
	public int computeLength() {
		// u1: tag
		// u2: utf8_index
		return 3;
	}
}
