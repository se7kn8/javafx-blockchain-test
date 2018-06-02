package com.github.se7kn8.blockchaintest;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class FXMLController {

	@FXML
	private HBox chain;

	private BlockChain blockChain;

	private int counter;

	@FXML
	void initialize() {
		blockChain = new BlockChain("First block", 2);
		blockChain.getBlocks().addListener((ListChangeListener.Change<? extends Block> change) -> {
			if (change.next()) {
				for (Block block : change.getAddedSubList()) {
					VBox blockBox = new VBox();
					blockBox.setStyle(
							"-fx-background-color: lightgrey; " +
									"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
					blockBox.getChildren().add(new Label("Block " + counter++));
					Label prevHashLabel = new Label();
					prevHashLabel.textProperty().bind(block.previousHashProperty());
					blockBox.getChildren().add(prevHashLabel);
					Label nonceLabel = new Label();
					Bindings.bindBidirectional(nonceLabel.textProperty(), block.nonceProperty(), new NumberStringConverter());
					blockBox.getChildren().add(nonceLabel);
					Label dataLabel = new Label();
					dataLabel.textProperty().bind(block.dataProperty());
					blockBox.getChildren().add(dataLabel);
					Label timestampLabel = new Label();
					Bindings.bindBidirectional(timestampLabel.textProperty(), block.timeStampProperty(), new NumberStringConverter());
					blockBox.getChildren().add(timestampLabel);
					Label hashLabel = new Label();
					hashLabel.textProperty().bind(block.hashProperty());
					blockBox.getChildren().add(hashLabel);
					chain.getChildren().add(blockBox);
				}
			}
		});
	}

	@FXML
	void onAddBlock() {
		FXUtil.showInputDialog("Block data?").ifPresent(data -> {
			Block block = blockChain.createBlock(data);
			Stage stage = FXUtil.showBlockGeneration(block);
			new Thread(() -> {
				block.mineBlock(blockChain.getDifficulty());
				Platform.runLater(() -> {
					stage.close();
					blockChain.addBlock(block);
				});
			}).start();
		});
	}

}
