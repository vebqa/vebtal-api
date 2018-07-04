package org.vebqa.vebtal;

import org.vebqa.vebtal.model.CommandResult;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

@SuppressWarnings("restriction")
public abstract class AbstractTestAdaptionPlugin implements TestAdaptionPlugin {
	
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
	
	protected Tab createTab(String aTabIdentifier, TableView<CommandResult> commandList, ObservableList<CommandResult> clData) {
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
//		hbox.getChildren().addAll(btnClear);
		
		// Table bauen
		TableColumn selCommand = new TableColumn("Command");
		selCommand.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("command"));
		selCommand.setSortable(false);
		selCommand.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));

		TableColumn selTarget = new TableColumn("Target");
		selTarget.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("target"));
		selTarget.setSortable(false);
		selTarget.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));

		TableColumn selValue = new TableColumn("Value");
		selValue.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("value"));
		selValue.setSortable(false);
		selValue.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));

		TableColumn selResult = new TableColumn("Result");
		selResult.setCellValueFactory(new PropertyValueFactory<CommandResult, Image>("result"));
		selResult.setSortable(false);
		selResult.prefWidthProperty().bind(commandList.widthProperty().multiply(0.10));

		TableColumn selInfo = new TableColumn("LogInfo");
		selInfo.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("loginfo"));
		selInfo.setSortable(false);
		selInfo.prefWidthProperty().bind(commandList.widthProperty().multiply(0.45));

		commandList.setItems(clData);
		commandList.getColumns().addAll(selCommand, selTarget, selValue, selResult, selInfo);

		
		final ContextMenu tableContextMenu = new ContextMenu();
		final MenuItem clearMenuItem = new MenuItem("Clear all");
		Image imgClear = new Image("/images/gui/trash-2x.png");
		clearMenuItem.setGraphic(new ImageView(imgClear));
		clearMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				clData.clear();
			}
		});
		
		tableContextMenu.getItems().addAll(clearMenuItem);
		commandList.setContextMenu(tableContextMenu);
		
		// einfuegen
		root.setTop(hbox);
		root.setCenter(commandList);
		genericTab.setContent(root);

		return genericTab;
	}
}
