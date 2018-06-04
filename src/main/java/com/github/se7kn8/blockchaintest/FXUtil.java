package com.github.se7kn8.blockchaintest;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class FXUtil {
	public static Optional<String> showInputDialog(String text) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(text);
		dialog.setTitle("");
		dialog.setContentText("");
		return dialog.showAndWait();
	}

	public static void showInfoDialog(String text) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
		alert.setHeaderText(text);
		alert.setTitle("");
		alert.show();
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

		Stage stage = new Stage(StageStyle.UNIFIED);
		stage.setAlwaysOnTop(true);
		stage.setScene(scene);
		stage.show();
		return stage;
	}

	public static Optional<Transaction> showTransactionDialog(List<Wallet> wallets) {
		Dialog<Transaction> dialog = new Dialog<>();
		dialog.setTitle("");
		dialog.setHeaderText("Create block");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField from = new TextField();
		TextField to = new TextField();
		TextField money = new TextField();
		money.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				money.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		grid.add(new Label("From:"), 0, 0);
		grid.add(from, 1, 0);
		grid.add(new Label("To:"), 0, 1);
		grid.add(to, 1, 1);
		grid.add(new Label("Money:"), 0, 2);
		grid.add(money, 1, 2);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.APPLY) {
				if (containsListWallet(wallets, from.getText()) && containsListWallet(wallets, to.getText())) {
					return new Transaction(from.getText(), to.getText(), Float.valueOf(money.getText()));
				}
			}
			return null;

		});

		return dialog.showAndWait();
	}

	private static boolean containsListWallet(List<Wallet> wallets, String walletName) {
		for (Wallet wallet : wallets) {
			if (wallet.getName().equalsIgnoreCase(walletName)) {
				return true;
			}
		}
		showInfoDialog("Wallet '" + walletName + "' could not be found!");
		return false;
	}

	public static Optional<Wallet> showWalletCreatingDialog() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("");
		dialog.setHeaderText("Create wallet");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		TextField money = new TextField();
		money.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				money.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		grid.add(new Label("Name:"), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Money:"), 0, 1);
		grid.add(money, 1, 1);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> new Pair<>(name.getText(), money.getText()));

		Optional<Pair<String, String>> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().getValue().equals("")) {
			return Optional.of(new Wallet(result.get().getKey(), Float.valueOf(result.get().getValue())));
		}
		return Optional.empty();
	}

}
