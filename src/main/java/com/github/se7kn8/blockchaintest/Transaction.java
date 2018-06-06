package com.github.se7kn8.blockchaintest;

public class Transaction {
	private Wallet sender;
	private Wallet receiver;
	private double money;

	public Transaction(Wallet sender, Wallet receiver, double money) {
		this.sender = sender;
		this.receiver = receiver;
		this.money = money;
	}

	public Wallet getSender() {
		return sender;
	}

	public Wallet getReceiver() {
		return receiver;
	}

	public double getMoney() {
		return money;
	}

	@Override
	public String toString() {
		return "from '" + sender.getName() + "' to '" + receiver.getName() + "' " + money;
	}
}
