package com.github.se7kn8.blockchaintest;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

	public static Optional<Transaction> showTransactionDialog(ObservableList<Wallet> wallets) {
		Dialog<Transaction> dialog = new Dialog<>();
		dialog.setTitle("");
		dialog.setHeaderText("Create block");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<Wallet> sender = new ComboBox<>();
		sender.setItems(wallets);
		sender.setCellFactory(cell -> new Wallet.WalletCell());
		sender.setButtonCell(new Wallet.WalletCell());
		ComboBox<Wallet> receiver = new ComboBox<>();
		receiver.setItems(wallets);
		receiver.setCellFactory(cell -> new Wallet.WalletCell());
		receiver.setButtonCell(new Wallet.WalletCell());
		Spinner<Double> money = new Spinner<>(Float.MIN_VALUE, Float.MAX_VALUE, 0);
		money.setEditable(true);
		money.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				money.increment(0);
			}
		});//Workaround for: https://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user

		grid.add(new Label("From:"), 0, 0);
		grid.add(sender, 1, 0);
		grid.add(new Label("To:"), 0, 1);
		grid.add(receiver, 1, 1);
		grid.add(new Label("Money:"), 0, 2);
		grid.add(money, 1, 2);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.APPLY) {
				if (sender.getSelectionModel().getSelectedItem() != null && receiver.getSelectionModel().getSelectedItem() != null) {
					return new Transaction(sender.getSelectionModel().getSelectedItem(), receiver.getSelectionModel().getSelectedItem(), money.getValueFactory().getValue());
				}
			}
			return null;

		});

		return dialog.showAndWait();
	}

	public static Optional<Wallet> showWalletCreatingDialog() {
		Dialog<Wallet> dialog = new Dialog<>();
		dialog.setTitle("");
		dialog.setHeaderText("Create wallet");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		Spinner<Double> money = new Spinner<>(Double.MIN_VALUE, Double.MAX_VALUE, 0);
		money.setEditable(true);
		money.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				money.increment(0);
			}
		});//Workaround for: https://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user

		grid.add(new Label("Name:"), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Money:"), 0, 1);
		grid.add(money, 1, 1);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.APPLY) {
				System.out.println(money.getValue());
				return new Wallet(name.getText(), money.getValueFactory().getValue());
			}
			return null;
		});

		return dialog.showAndWait();
	}

}
