package br.com.javacia.merge.util.inspector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import br.com.javacia.merge.util.Difference;
import br.com.javacia.merge.util.DifferenceInspector;
import br.com.javacia.merge.util.type.PropertiesType;
import br.com.javacia.merge.util.type.PropertiesType.PropertiesTypes;

public class KeyPropertiesDifferenceInspector implements DifferenceInspector {

	private Properties propLocal;
	private Properties propRemote;

	public KeyPropertiesDifferenceInspector(File fileLocal, File fileRemote) {
		propLocal = loadProperties(fileLocal);
		propRemote = loadProperties(fileRemote);
	}

	public KeyPropertiesDifferenceInspector(Reader readerLocal, Reader readerRemote) {
		propLocal = loadProperties(readerLocal);
		propRemote = loadProperties(readerRemote);
	}

	public List<Difference> findDifferences() {
		List<Difference> differences = new ArrayList<Difference>();
		Set<String> keyLocal = propLocal.stringPropertyNames();
		for (String name : keyLocal) {

			String valueRemote = propRemote.getProperty(name, "");
			String valueLocal = propLocal.getProperty(name, "");
			if ("".equals(valueRemote)) {
				differences.add(new Difference(new PropertiesType(PropertiesTypes.PROPERTIES), name, valueLocal));
			}

		}

		return differences;
	}

	private Properties loadProperties(Reader reader) {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(reader);
		} catch (IOException ex) {
			properties = null;
		}
		return properties;
	}

	private Properties loadProperties(File file) {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(new FileInputStream(file));
		} catch (IOException ex) {
			properties = null;
		}
		return properties;
	}

}