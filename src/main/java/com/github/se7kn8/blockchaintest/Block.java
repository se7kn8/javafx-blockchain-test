package com.github.se7kn8.blockchaintest;

import com.google.common.hash.Hashing;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Block {

	private StringProperty hash;
	private StringProperty previousHash;
	private StringProperty data;
	private LongProperty timeStamp;

	private IntegerProperty nonce = new SimpleIntegerProperty();

	public Block(String data, String previousHash) {
		this.data = new SimpleStringProperty(data);
		this.previousHash = new SimpleStringProperty(previousHash);
		this.timeStamp = new SimpleLongProperty(new Date().getTime());
		this.hash = new SimpleStringProperty(calcHash());
	}

	public StringProperty dataProperty() {
		return data;
	}

	public void setData(String data) {
		this.data.set(data);
	}

	public String getData() {
		return data.get();
	}

	public StringProperty hashProperty() {
		return hash;
	}

	public String getHash() {
		return hash.get();
	}

	public void setHash(String hash) {
		this.hash.set(hash);
	}

	public StringProperty previousHashProperty() {
		return previousHash;
	}

	public String getPreviousHash() {
		return previousHash.get();
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash.set(previousHash);
	}

	public IntegerProperty nonceProperty() {
		return nonce;
	}

	public int getNonce() {
		return nonce.get();
	}

	public void setNonce(int nonce) {
		this.nonce.set(nonce);
	}

	public long getTimeStamp() {
		return timeStamp.get();
	}

	public LongProperty timeStampProperty() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp.set(timeStamp);
	}

	public String calcHash() {
		return Hashing.sha256().hashString(getPreviousHash() + getTimeStamp() + getNonce() + getData(), StandardCharsets.UTF_8).toString();
	}

	public void mineBlock(int difficulty) {
		if (difficulty > 0) {
			String target = new String(new char[difficulty]).replace('\0', '0');
			while (!getHash().substring(0, difficulty).equals(target)) {
				int newNonce = getNonce() + 1;
				String hash = calcHash();
				Platform.runLater(() -> {
					setNonce(newNonce);
					setHash(hash);
				});
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		}
	}

}
