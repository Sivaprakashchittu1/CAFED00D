package me.coley.cafedude.classfile.attribute;

import me.coley.cafedude.classfile.behavior.CpAccessor;
import me.coley.cafedude.classfile.constant.CpEntry;
import me.coley.cafedude.classfile.constant.CpUtf8;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Method parameters attribute.
 */
public class MethodParametersAttribute extends Attribute {
	private List<Parameter> parameters;

	/**
	 * @param name
	 * 		Name index in constant pool.
	 * @param parameters
	 * 		Parameters.
	 */
	public MethodParametersAttribute(@Nonnull CpUtf8 name, @Nonnull List<Parameter> parameters) {
		super(name);
		this.parameters = parameters;
	}

	/**
	 * @return Parameters.
	 */
	@Nonnull
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 * 		New parameters.
	 */
	public void setParameters(@Nonnull List<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Nonnull
	@Override
	public Set<CpEntry> cpAccesses() {
		Set<CpEntry> set = super.cpAccesses();
		for (Parameter p : parameters)
			set.addAll(p.cpAccesses());
		return set;
	}

	@Override
	public int computeInternalLength() {
		// U1: parameterCount
		// (U2: name, U2: accessFlags) * parameterCount
		return 1 + (parameters.size() * 4);
	}

	/**
	 * Method parameter.
	 */
	public static class Parameter implements CpAccessor {
		private int accessFlags;
		private CpUtf8 name;

		/**
		 * @param accessFlags
		 * 		Access flags.
		 * @param name
		 * 		Name index in constant pool.
		 */
		public Parameter(int accessFlags, @Nonnull CpUtf8 name) {
			this.accessFlags = accessFlags;
			this.name = name;
		}

		/**
		 * @return Access flags.
		 */
		public int getAccessFlags() {
			return accessFlags;
		}

		/**
		 * @param accessFlags
		 * 		New access flags.
		 */
		public void setAccessFlags(int accessFlags) {
			this.accessFlags = accessFlags;
		}

		/**
		 * @return Name index in constant pool.
		 */
		@Nonnull
		public CpUtf8 getName() {
			return name;
		}

		/**
		 * @param name
		 * 		New name index in constant pool.
		 */
		public void setName(@Nonnull CpUtf8 name) {
			this.name = name;
		}

		@Nonnull
		@Override
		public Set<CpEntry> cpAccesses() {
			Set<CpEntry> set = new HashSet<>();
			set.add(name);
			return set;
		}
	}
}
