package org.vebqa.vebtal;

import org.vebqa.vebtal.model.CommandResult;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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

	/**
	 * commands to execute
	 */
	protected static final TableView<CommandResult> commandList = new TableView<>();
	
	/**
	 * results after execution
	 */
	protected static final ObservableList<CommandResult> clData = FXCollections.observableArrayList();	
	
	/** Clear Button **/
	private static final Button btnClear = new Button();
	
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
		Platform.runLater(() -> btnClear.setDisable(aState));
	}
	
	protected Tab createTab(String aTabIdentifier) {
		// Richtet den Plugin-spezifischen Tab ein

		Tab pdfTab = new Tab();
		pdfTab.setText(aTabIdentifier);
		pdfTab.setId(aTabIdentifier);
		
		Image imgTabStatus = new Image("/images/gui/ban-2x.png");
		pdfTab.setGraphic(new ImageView(imgTabStatus));
		
		// LogBox
		BorderPane root = new BorderPane();

		// Top bauen
		HBox hbox = new HBox();

		Image imgClear = new Image("/images/gui/trash-2x.png");
		
		btnClear.setText("Clear");
		btnClear.setGraphic(new ImageView(imgClear));
		btnClear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				clData.clear();
			}
		});
		
		hbox.getChildren().addAll(btnClear);
		
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

		// einfuegen
		root.setTop(hbox);
		root.setCenter(commandList);
		pdfTab.setContent(root);

		return pdfTab;
	}
}
