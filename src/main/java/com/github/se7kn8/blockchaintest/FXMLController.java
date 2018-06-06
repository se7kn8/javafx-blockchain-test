package com.github.se7kn8.blockchaintest;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class FXMLController {

	@FXML
	private FlowPane chain;

	@FXML
	private ListView<Wallet> wallets;

	private BlockChain blockChain;

	private int counter;

	@FXML
	private void initialize() {
		blockChain = new BlockChain(2);
		blockChain.getBlocks().addListener((ListChangeListener.Change<? extends Block> change) -> {
			if (change.next()) {
				if (change.getAddedSize() > 0) {
					for (Block block : change.getAddedSubList()) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(ClassLoader.getSystemResource("fxml/BlockInfo.fxml"));
						loader.setController(new FXMLBlockController(block, "Block " + counter++, blockChain, chain));
						try {
							GridPane pane = loader.load();
							pane.getStyleClass().add("grid-pane");
							pane.getStylesheets().add(ClassLoader.getSystemResource("css/valid-block.css").toExternalForm());
							chain.getChildren().add(pane);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		wallets.setCellFactory(cell -> new Wallet.WalletCell());
	}

	@FXML
	private void onAddBlock() {
		Optional<Transaction> transaction = FXUtil.showTransactionDialog(wallets.getItems());
		if (transaction.isPresent()) {
			Wallet fromWallet = getWalletByName(transaction.get().getFrom());
			fromWallet.setMoney(fromWallet.getMoney() - transaction.get().getMoney());
			Wallet toWallet = getWalletByName(transaction.get().getTo());
			toWallet.setMoney(toWallet.getMoney() + transaction.get().getMoney());
			wallets.refresh();
			String blockData = transaction.get().toString();
			Block block;
			if (blockChain.getBlocks().size() == 0) {
				block = blockChain.createGenesisBlock(blockData);
			} else {
				block = blockChain.createBlock(blockData);
			}
			Stage stage = FXUtil.showBlockGeneration(block);
			new Thread(() -> {
				block.mineBlock(blockChain.getDifficulty());
				Platform.runLater(() -> {
					stage.close();
					blockChain.addBlock(block);
				});
			}).start();

		}
	}

	@FXML
	private void onValidateBlockChain() {
		BlockChain.BlockChainValidationResult result = blockChain.isChainValid();
		resetChainColor();
		if (result.isValid()) {
			FXUtil.showInfoDialog("BlockChain is valid");
		} else {
			for (int i = result.getInvalidPos(); i < chain.getChildren().size(); i++) {
				Node node = chain.getChildren().get(i);
				if (node instanceof GridPane) {
					((GridPane) node).getStylesheets().clear();
					((GridPane) node).getStylesheets().add(ClassLoader.getSystemResource("css/invalid-block.css").toExternalForm());
				}
			}
		}
	}

	private void resetChainColor() {
		chain.getChildren().forEach(node -> {
			if (node instanceof GridPane) {
				((GridPane) node).getStylesheets().clear();
				((GridPane) node).getStylesheets().add(ClassLoader.getSystemResource("css/valid-block.css").toExternalForm());
			}
		});
	}

	private Wallet getWalletByName(String walletName) {
		for (Wallet wallet : wallets.getItems()) {
			if (wallet.getName().equalsIgnoreCase(walletName)) {
				return wallet;
			}
		}
		return null;
	}

	@FXML
	private void onAddWalletClicked() {
		FXUtil.showWalletCreatingDialog().ifPresent(wallet -> wallets.getItems().add(wallet));
	}

}
