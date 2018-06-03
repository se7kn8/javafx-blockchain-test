package com.github.se7kn8.blockchaintest;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.converter.NumberStringConverter;

public class FXMLBlockController {

	private Block block;
	private String id;

	public FXMLBlockController(Block block, String id) {
		this.block = block;
		this.id = id;
	}

	@FXML
	private Label blockID;

	@FXML
	private Label hash;

	@FXML
	private Label prevHash;

	@FXML
	private Label time;

	@FXML
	private Label nonce;

	@FXML
	private Label data;

	@FXML
	private void initialize() {
		blockID.setText(id);
		hash.textProperty().bind(block.hashProperty());
		prevHash.textProperty().bind(block.previousHashProperty());
		Bindings.bindBidirectional(time.textProperty(), block.timeStampProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(nonce.textProperty(), block.nonceProperty(), new NumberStringConverter());
		data.textProperty().bind(block.dataProperty());
	}

	@FXML
	private void onEditDataClicked() {
		FXUtil.showInputDialog("New block data?").ifPresent(newData -> {
			block.setData(newData);
		});
	}
}
