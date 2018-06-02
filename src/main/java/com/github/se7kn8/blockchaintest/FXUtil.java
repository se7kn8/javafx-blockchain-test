package com.github.se7kn8.blockchaintest;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class FXUtil {
	public static Optional<String> showInputDialog(String text) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(text);
		dialog.setTitle("");
		dialog.setContentText("");
		return dialog.showAndWait();
	}

	public static Stage showBlockGeneration(Block block) {
		VBox root = new VBox();
		root.getChildren().add(new ProgressIndicator());

		Label nonceLbl = new Label();
		Label hashLbl = new Label();

		block.nonceProperty().addListener((observable, oldValue, newValue) -> {
			nonceLbl.setText(String.valueOf(newValue.intValue()));
		});
		hashLbl.textProperty().bind(block.hashProperty());

		root.getChildren().add(nonceLbl);
		root.getChildren().add(hashLbl);

		Scene scene = new Scene(root);

		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setScene(scene);
		stage.show();
		return stage;
	}
}
