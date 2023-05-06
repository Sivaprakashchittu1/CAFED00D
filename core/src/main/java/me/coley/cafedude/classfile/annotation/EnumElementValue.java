package me.coley.cafedude.classfile.annotation;

import me.coley.cafedude.classfile.constant.CpEntry;
import me.coley.cafedude.classfile.constant.CpUtf8;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * Enum element value.
 *
 * @author Matt Coley
 */
public class EnumElementValue extends ElementValue {
	private CpUtf8 type;
	private CpUtf8 name;

	/**
	 * @param tag
	 * 		ASCII tag representation, must be {@code e}.
	 * @param type
	 * 		Index of enum type descriptor constant.
	 * @param name
	 * 		Index of enum value name constant.
	 */
	public EnumElementValue(char tag, @Nonnull CpUtf8 type, @Nonnull CpUtf8 name) {
		super(tag);
		if (tag != 'e')
			throw new IllegalArgumentException("UTF8 element value must have 'e' tag");
		this.type = type;
		this.name = name;
	}

	/**
	 * @return Index of enum type descriptor constant.
	 */
	@Nonnull
	public CpUtf8 getType() {
		return type;
	}

	/**
	 * @param type
	 * 		Index of enum type descriptor constant.
	 */
	public void setType(@Nonnull CpUtf8 type) {
		this.type = type;
	}

	/**
	 * @return Index of enum value name constant.
	 */
	@Nonnull
	public CpUtf8 getName() {
		return name;
	}

	/**
	 * @param name
	 * 		Index of enum value name constant.
	 */
	public void setName(@Nonnull CpUtf8 name) {
		this.name = name;
	}

	/**
	 * @return ASCII tag representation of an enum, {@code e}.
	 */
	@Override
	public char getTag() {
		return super.getTag();
	}

	@Nonnull
	@Override
	public Set<CpEntry> cpAccesses() {
		Set<CpEntry> set = new HashSet<>();
		set.add(name);
		set.add(type);
		return set;
	}

	@Override
	public int computeLength() {
		// u1: tag
		// u2: enum_type_index
		// u2: enum_name_index
		return 5;
	}
}
