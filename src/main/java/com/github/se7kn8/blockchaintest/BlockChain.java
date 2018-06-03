package com.github.se7kn8.blockchaintest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

	private ObservableList<Block> blocks = FXCollections.observableArrayList();
	private int difficulty;

	public BlockChain(String genesisData, int difficulty) {
		this.difficulty = difficulty;
	}

	public Block createGenesisBlock(String data){
		return new Block(data, "0");
	}

	public Block createBlock(String data) {
		return new Block(data, blocks.get(blocks.size() - 1).getHash());
	}

	public void addBlock(Block block) {
		this.blocks.add(block);
	}

	public ObservableList<Block> getBlocks() {
		return blocks;
	}

	public boolean isChainValid() {
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blocks.size(); i++) {
			Block currentBlock = blocks.get(i);
			Block previousBlock = blocks.get(i - 1);

			if (!currentBlock.getHash().equals(currentBlock.calcHash())) {
				System.err.println("Block on position " + i + " has an invalid hash");
				return false;
			}
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.err.println("Block on position " + i + " has an invalid previous hash");
				return false;
			}
			if (difficulty > 0) {
				if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
					System.err.println("Block on position " + i + " hasn't been minded");
					return false;
				}
			}

		}
		return true;
	}

	public int getDifficulty() {
		return difficulty;
	}
}
