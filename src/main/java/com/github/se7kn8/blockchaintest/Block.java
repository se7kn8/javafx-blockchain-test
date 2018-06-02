package com.github.se7kn8.blockchaintest;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Block {

	private String hash;
	private String previousHash;
	private String data;
	private int nonce;
	private long timeStamp;

	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calcHash();
	}

	public void setData(String data) {
		this.data = data;
	}

	public String calcHash() {
		return Hashing.sha256().hashString(previousHash + timeStamp + nonce + data, StandardCharsets.UTF_8).toString();
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public String getData() {
		return data;
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void mineBlock(int difficulty) {
		if (difficulty > 0) {
			String target = new String(new char[difficulty]).replace('\0', '0');
			while (!hash.substring(0, difficulty).equals(target)) {
				nonce++;
				hash = calcHash();
			}
		}
	}

}
