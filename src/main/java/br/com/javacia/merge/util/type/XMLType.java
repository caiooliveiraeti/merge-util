package br.com.javacia.merge.util.type;

import br.com.javacia.merge.util.Type;

public class XMLType implements Type {
	public enum XMLTypes {
		PROPERTIES, VALUES
	}

	public XMLType(XMLTypes type) {
		this.type = type;
	}

	public String getName() {
		return type.name();
	}

	private XMLTypes type;
}