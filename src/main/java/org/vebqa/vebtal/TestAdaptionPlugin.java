package org.vebqa.vebtal;

import org.apache.commons.configuration2.FileBasedConfiguration;

import javafx.scene.control.Tab;

@SuppressWarnings("restriction")
public interface TestAdaptionPlugin {

	TestAdaptionType getType();
	
	String getName();
	
	Tab startup();
	
	FileBasedConfiguration loadConfigString();
	
	boolean shutdown();
	
	Class<?> getImplementation();
	
	String getAdaptionID();
}
