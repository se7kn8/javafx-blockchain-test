package com.github.se7kn8.blockchaintest;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class FXMLBlockController {

	private Block block;
	private String id;
	private BlockChain chain;
	private FlowPane pane;

	public FXMLBlockController(Block block, String id, BlockChain chain, FlowPane pane) {
		this.block = block;
		this.id = id;
		this.chain = chain;
		this.pane = pane;
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

	@FXML
	private void onMineClicked() {
		block.setPreviousHash(chain.getBlocks().get(chain.getBlockPos(block) - 1).getHash());
		new Thread(() -> block.mineBlock(chain.getDifficulty())).start();
	}

	@FXML
	private void onDeleteClicked() {
		int pos = chain.getBlockPos(block);
		chain.getBlocks().remove(pos);
		pane.getChildren().remove(pos);
	}


}
