package org.vebqa.vebtal;

import java.io.File;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.CommandResult;
import org.vebqa.vebtal.model.CommandType;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public abstract class AbstractTestAdaptionPlugin implements TestAdaptionPlugin {

	private static final Logger logger = LoggerFactory.getLogger(AbstractTestAdaptionPlugin.class);
	
	protected TestAdaptionType adaptionType;

	public AbstractTestAdaptionPlugin() {
		throw new UnsupportedOperationException("Use constructor without setting the adaption type is forbidden.");
	}

	public AbstractTestAdaptionPlugin(TestAdaptionType aType) {
		this.adaptionType = aType;
	}

	public TestAdaptionType getType() {
		if (adaptionType == null) {
			throw new UnsupportedOperationException("Adaption type has to be defined before!");
		} else {
			return adaptionType;
		}
	}

	public Tab startup() {
		throw new UnsupportedOperationException("startup not yet implemented.");
	}

	public boolean shutdown() {
		throw new UnsupportedOperationException("shutdown not yet implemented.");
	}

	public Class<?> getImplementation() {
		throw new UnsupportedOperationException("not yet implemented.");
	}

	public String getAdaptionID() {
		throw new UnsupportedOperationException("not yet imlemented.");
	}

	public static void setDisableUserActions(boolean aState) {
		// Platform.runLater(() -> btnClear.setDisable(aState));
	}

	protected Tab createTab(String aTabIdentifier, TableView<CommandResult> commandList,
			ObservableList<CommandResult> clData) {
		// Richtet den Plugin-spezifischen Tab ein

		Tab genericTab = new Tab();
		genericTab.setText(aTabIdentifier);
		genericTab.setId(aTabIdentifier);

		Image imgTabStatus = new Image("/images/gui/ban-2x.png");
		genericTab.setGraphic(new ImageView(imgTabStatus));

		// LogBox
		BorderPane root = new BorderPane();

		// Top bauen
		HBox hbox = new HBox();
		hbox.setId("table");

		// Table bauen
		TableColumn selCommandType = new TableColumn("Type");
		selCommandType.setCellValueFactory(new PropertyValueFactory<CommandResult, CommandType>("type"));
		selCommandType.setCellFactory(new CommandTypeCellFactory());
		selCommandType.setSortable(false);
		selCommandType.setPrefWidth(36); // fixed width!

		TableColumn selCommand = new TableColumn("Command");
		selCommand.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("command"));
		selCommand.setSortable(false);
		// selCommand.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));
		selCommand.setMinWidth(commandList.getPrefWidth() * 0.15);
		
		TableColumn selTarget = new TableColumn("Target");
		selTarget.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("target"));
		selTarget.setSortable(false);
		// selTarget.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));
		selTarget.setMinWidth(commandList.getPrefWidth() * 0.15);
		
		TableColumn selValue = new TableColumn("Value");
		selValue.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("value"));
		selValue.setSortable(false);
		// selValue.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));
		selValue.setMinWidth(commandList.getPrefWidth() * 0.15);
		
		TableColumn selResult = new TableColumn("Result");
		selResult.setCellValueFactory(new PropertyValueFactory<CommandResult, Image>("result"));
		selResult.setSortable(false);
		// selResult.prefWidthProperty().bind(commandList.widthProperty().multiply(0.10));
		selResult.setMinWidth(commandList.getPrefWidth() * 0.10);
		
		TableColumn selInfo = new TableColumn("LogInfo");
		selInfo.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("loginfo"));
		selInfo.setSortable(false);
		// selInfo.prefWidthProperty().bind(commandList.widthProperty().multiply(0.45));
		selInfo.setMinWidth(commandList.getPrefWidth() * 0.45);
		
		commandList.setItems(clData);
		commandList.getColumns().addAll(selCommandType, selCommand, selTarget, selValue, selResult, selInfo);

		// we need to define a context menu
		final ContextMenu tableContextMenu = new ContextMenu();
		
		// we need a clear all button
		final MenuItem clearMenuItem = new MenuItem("Clear all");
		Image imgClear = new Image("/images/gui/trash-2x.png");
		clearMenuItem.setGraphic(new ImageView(imgClear));
		clearMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				clData.clear();
			}
		});

		// we want to copy cells
		commandList.getSelectionModel().setCellSelectionEnabled(true);
		commandList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		final MenuItem copyMenuItem = new MenuItem("Copy");
		Image imgCopy = new Image("/images/gui/copy.png");
		copyMenuItem.setGraphic(new ImageView(imgCopy));
				
		copyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        ObservableList<TablePosition> posList = commandList.getSelectionModel().getSelectedCells();
		        int old_r = -1;
		        StringBuilder clipboardString = new StringBuilder();
		        for (TablePosition p : posList) {
		            int r = p.getRow();
		            int c = p.getColumn();
		            Object cell = commandList.getColumns().get(c).getCellData(r);
		            if (cell == null)
		                cell = "";
		            if (old_r == r)
		                clipboardString.append('\t');
		            else if (old_r != -1)
		                clipboardString.append('\n');
		            clipboardString.append(cell);
		            old_r = r;
		        }
		        final ClipboardContent content = new ClipboardContent();
		        content.putString(clipboardString.toString());
		        Clipboard.getSystemClipboard().setContent(content);
		    }
		});
		
		tableContextMenu.getItems().addAll(clearMenuItem, copyMenuItem);
		commandList.setContextMenu(tableContextMenu);

		// einfuegen
		root.setTop(hbox);
		root.setCenter(commandList);
		genericTab.setContent(root);

		return genericTab;
	}
	
	protected CombinedConfiguration loadConfig(String aTabIdentifier) {
		Parameters params = new Parameters();
		
		// load default configuration from root
		String tPropertiesCoreName = aTabIdentifier + ".properties";
		File tPropertiesCore = new File("./" + tPropertiesCoreName);
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderCore = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class)
						.configure(params.properties().setFile(tPropertiesCore));
		FileBasedConfiguration configCore = null;

		try {
			configCore = builderCore.getConfiguration();
		} catch (ConfigurationException e) {
			logger.error("Couldnt load configuration file: {} because of {}", tPropertiesCore.getAbsolutePath(), e.getMessage());
		}

		// load user configuration from /conf folder
		String tPropertiesUserName = aTabIdentifier + "_user.properties";
		File tPropertiesUser = new File("./conf/" + tPropertiesUserName);
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderUser = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class)
						.configure(params.properties().setFile(tPropertiesUser));
		FileBasedConfiguration configUser = null;

		try {
			configUser = builderUser.getConfiguration();
		} catch (ConfigurationException e) {
			logger.error("Couldnt load configuration file: {} because of {}", tPropertiesUser.getAbsolutePath(), e.getMessage());
		}
		
		
		// use an override strategy
		CombinedConfiguration configCombined = new CombinedConfiguration(new OverrideCombiner());
		// load user configuration first
		if (configUser != null) {
			configCombined.addConfiguration(configUser);
		}
		// core config is added
		if (configCore != null) {
			configCombined.addConfiguration(configCore);
		}
		return configCombined;
	}	
}
