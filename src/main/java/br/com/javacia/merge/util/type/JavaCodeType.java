package br.com.javacia.merge.util.type;

import br.com.javacia.merge.util.Type;

public class JavaCodeType implements Type {
	public enum JavaCodeTypes {
		METHOD, FIELD, PARAMETER
	}

	public JavaCodeType(JavaCodeTypes type) {
		this.type = type;
	}

	public String getName() {
		return type.name();
	}

	private JavaCodeTypes type;
}