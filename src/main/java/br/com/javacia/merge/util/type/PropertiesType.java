package br.com.javacia.merge.util.type;

import br.com.javacia.merge.util.Type;

public class PropertiesType implements Type {

	public enum PropertiesTypes {
		PROPERTIES
	}

	public PropertiesType(PropertiesTypes type) {
		this.type = type;
	}

	public String getName() {
		return type.name();
	}

	private PropertiesTypes type;
}