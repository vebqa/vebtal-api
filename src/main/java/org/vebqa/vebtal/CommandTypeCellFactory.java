package org.vebqa.vebtal;

import org.vebqa.vebtal.model.CommandType;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

@SuppressWarnings("restriction")
public class CommandTypeCellFactory implements Callback<TableColumn, TableCell> {

    public CommandTypeCellFactory() {
    }

    @Override
    public TableCell call(TableColumn p) {
        TableCell<Object, CommandType> cell
                = new TableCell<Object, CommandType>() {

                    private final VBox vbox;
                    private final ImageView imageview;

                    // Constructor
                    {
                        vbox = new VBox();
                        vbox.setAlignment(Pos.CENTER);
                        imageview = new ImageView();
                        imageview.setFitHeight(16);
                        imageview.setFitWidth(16);
                        imageview.setVisible(true);
                        imageview.setCache(true);
                        vbox.getChildren().addAll(imageview);
                        setGraphic(vbox);
                    }

                    @Override
                    protected void updateItem(CommandType item,
                            boolean empty) {

                        // calling super here is very important - don't skip this!
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                            setGraphic(null);

                        } else {
                        	Image image;
                        	if (item == CommandType.ACCESSOR) {
                        		image = new Image("/images/gui/arrow-left-2x.png");
                        	} else if (item == CommandType.ACTION) {
                        		image = new Image("/images/gui/arrow-right-2x.png");
                        	} else if (item == CommandType.ASSERTION) {
                        		image = new Image("/images/gui/eye-2x.png");
                        	} else {
                        		image = new Image("/images/gui/question-mark-2x.png");
                        	}

                            imageview.setImage(image);
                            setGraphic(vbox);
                        }
                    }
                };

        return cell;
    }
}
