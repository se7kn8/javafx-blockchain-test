package com.github.se7kn8.blockchaintest;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;

public class Wallet {

	public static class WalletCell extends ListCell<Wallet> {
		@Override
		protected void updateItem(Wallet item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && item != null) {
				setText(item.getName() + " [" + item.getMoney() + "]");
			} else {
				setText(null);
			}
		}
	}

	private StringProperty name = new SimpleStringProperty();
	private FloatProperty money = new SimpleFloatProperty();

	public Wallet(String name, float money) {
		this.name.set(name);
		this.money.set(money);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setMoney(float money) {
		this.money.set(money);
	}

	public String getName() {
		return name.get();
	}

	public float getMoney() {
		return money.get();
	}

	public FloatProperty moneyProperty() {
		return money;
	}

	public StringProperty nameProperty() {
		return name;
	}
}
