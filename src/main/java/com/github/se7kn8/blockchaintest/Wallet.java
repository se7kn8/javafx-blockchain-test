package com.github.se7kn8.blockchaintest;

import javafx.beans.property.*;
import javafx.scene.control.ListCell;

import java.text.DecimalFormat;

public class Wallet {

	public static class WalletCell extends ListCell<Wallet> {

		private static final DecimalFormat format = new DecimalFormat("###.##");

		@Override
		protected void updateItem(Wallet item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && item != null) {
				setText(item.getName() + " [" + format.format(item.getMoney()) + "]");
			} else {
				setText(null);
			}
		}
	}

	private StringProperty name = new SimpleStringProperty();
	private DoubleProperty money = new SimpleDoubleProperty();

	public Wallet(String name, double money) {
		this.name.set(name);
		this.money.set(money);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setMoney(double money) {
		this.money.set(money);
	}

	public String getName() {
		return name.get();
	}

	public double getMoney() {
		return money.get();
	}

	public DoubleProperty moneyProperty() {
		return money;
	}

	public StringProperty nameProperty() {
		return name;
	}
}
