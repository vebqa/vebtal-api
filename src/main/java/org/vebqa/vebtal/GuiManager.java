package org.vebqa.vebtal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.ConfigurationCatalog;
import org.vebqa.vebtal.sut.SutStatus;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("restriction")
public class GuiManager {

	private static final Logger logger = LoggerFactory.getLogger(GuiManager.class);

	private static final GuiManager gui = new GuiManager();

	private CombinedConfiguration config = new CombinedConfiguration(new OverrideCombiner());
	
	private static final TableView<ConfigurationCatalog> configList = new TableView<>();
	private static final ObservableList<ConfigurationCatalog> configData = FXCollections.observableArrayList();

	
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
	
	public void showConfig() {
		ObservableList<Tab> tabs = mainTabPane.getTabs();
		for (final Tab aTab : tabs) {
			if (aTab.getId() == "config") {
				Iterator<String> keys = config.getKeys();
				// List<String> keyList = new ArrayList<String>();
				while(keys.hasNext()) {
					String aKey = keys.next();
					final ConfigurationCatalog tCC = new ConfigurationCatalog(aKey, config.getString(aKey));
					
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							configData.add(tCC);
						}
					});
				}
			}
		}
	}
	
	public Tab createConfigTab() {
		Tab genericTab = new Tab();
		genericTab.setText("Config");
		genericTab.setId("config");
		
		BorderPane root = new BorderPane();
		// Table bauen
		TableColumn confKey = new TableColumn("Key");
		confKey.setCellValueFactory(new PropertyValueFactory<ConfigurationCatalog, String>("key"));
		confKey.setSortable(false);
		// confKey.prefWidthProperty().bind(configList.widthProperty().multiply(0.25));
		confKey.setMinWidth(configList.getPrefWidth() * 0.25);
		
		TableColumn confValue = new TableColumn("Value");
		confValue.setCellValueFactory(new PropertyValueFactory<ConfigurationCatalog, String>("value"));
		confValue.setSortable(false);
		// confValue.prefWidthProperty().bind(configList.widthProperty().multiply(0.25));
		confValue.setMinWidth(configList.getPrefWidth() * 0.25);
		
		configList.setItems(configData);
		configList.getColumns().addAll(confKey, confValue);
		
		root.setCenter(configList);
		genericTab.setContent(root);
		
		return genericTab;
	}
}
