package org.vebqa.vebtal.model;

import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("restriction")
public class ConfigurationCatalog {
	private final SimpleStringProperty key;
	private final SimpleStringProperty value;

	public ConfigurationCatalog(String aKey, String aValue) {
		this.key = new SimpleStringProperty(aKey);
		this.value = new SimpleStringProperty(aValue);
	}

	public String getKey() {
		return key.get();
	}

	public String getValue() {
		return value.get();
	}
}
