package br.com.javacia.merge.util;

public class Difference {

	private Type type;
	private String valueRemote;
	private String valueLocal;
	private String name;

	public Difference(Type type, String name, String valueLocal,
			String valueRemote) {
		this.type = type;
		this.name = name;
		this.valueLocal = valueLocal;
		this.valueRemote = valueRemote;
	}

	public Difference(Type type, String name, String valueLocal) {
		this(type, name, valueLocal, null);
	}

	public boolean isConflict() {
		return valueRemote != null;
	}

	public Type getType() {
		return type;
	}

	public String getValueRemote() {
		return valueRemote;
	}

	public String getValueLocal() {
		return valueLocal;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return (new StringBuilder()).append("Difference [type=")
				.append(type.getName()).append(", name=").append(name)
				.append(", isConflict=").append(isConflict()).append("]")
				.toString();
	}

}