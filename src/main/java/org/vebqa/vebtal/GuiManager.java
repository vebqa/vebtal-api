package org.vebqa.vebtal;


import org.apache.commons.configuration2.CombinedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.sut.SutStatus;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("restriction")
public class GuiManager {

	private static final Logger logger = LoggerFactory.getLogger(GuiManager.class);

	private static final GuiManager gui = new GuiManager();

	private CombinedConfiguration config;
	
	private TabPane mainTabPane = new TabPane();

	private BorderPane mainPane = new BorderPane();
	
	/** Logs **/
	private TextArea textArea = new TextArea();

	public GuiManager() {
		logger.debug("Guimanager created.");
	}

	public static GuiManager getinstance() {
		return gui;
	}
	
	public TabPane getMainTab() {
		return mainTabPane;
	}
	
	public BorderPane getMain() {
		return mainPane;
	}
	
	public TextArea getLogArea() {
		return textArea;
	}
	
	public void writeLog(String someInfo) {
		Platform.runLater(() -> textArea.appendText(someInfo + "\n"));
	}
	
	public void setTabStatus(String anIdentifier, SutStatus aStatus) {
		ObservableList<Tab> tabs = mainTabPane.getTabs();
		boolean tabFound = false;
		for (final Tab aTab : tabs) {
			if (aTab.getId() == anIdentifier) {
				tabFound = true;
				if (aStatus == SutStatus.CONNECTED) {
					final Image imgTabStatus = new Image("/images/gui/transfer-2x.png");
					Platform.runLater(() -> aTab.setGraphic(new ImageView(imgTabStatus)));
				} else {
					final Image imgTabStatus = new Image("/images/gui/ban-2x.png");
					Platform.runLater(() -> aTab.setGraphic(new ImageView(imgTabStatus)));
				}
			}
		}
		if (!tabFound) {
			logger.warn("No tab found with identifier {}!", anIdentifier);
		}
	}
	
	public CombinedConfiguration getConfig() {
		return config;
	}
}
