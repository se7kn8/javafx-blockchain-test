package com.github.se7kn8.blockchaintest;

public class Transaction {
	private String from;
	private String to;
	private float money;

	public Transaction(String from, String to, float money) {
		this.from = from;
		this.to = to;
		this.money = money;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public float getMoney() {
		return money;
	}

	@Override
	public String toString() {
		return "from='" + from + '\'' +
				", to='" + to + '\'' +
				", money=" + money;
	}
}
