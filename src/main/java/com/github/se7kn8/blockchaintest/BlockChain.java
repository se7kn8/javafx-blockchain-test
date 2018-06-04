package com.github.se7kn8.blockchaintest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
	public enum ValidationResultType {
		INVALID_HASH,
		INVALID_PREVIOUS_HASH,
		NOT_MINED
	}

	public class BlockChainValidationResult {
		private boolean valid;
		private int invalidPos;
		private ValidationResultType errorType;

		public BlockChainValidationResult(boolean valid, int invalidPos, ValidationResultType errorType) {
			this.valid = valid;
			this.invalidPos = invalidPos;
			this.errorType = errorType;
		}

		public boolean isValid() {
			return valid;
		}

		public int getInvalidPos() {
			return invalidPos;
		}

		public ValidationResultType getErrorType() {
			return errorType;
		}
	}

	private ObservableList<Block> blocks = FXCollections.observableArrayList();
	private int difficulty;

	public BlockChain(int difficulty) {
		this.difficulty = difficulty;
	}

	public Block createGenesisBlock(String data) {
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

	public BlockChainValidationResult isChainValid() {
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blocks.size(); i++) {
			Block currentBlock = blocks.get(i);
			Block previousBlock = blocks.get(i - 1);

			if (!currentBlock.getHash().equals(currentBlock.calcHash())) {
				return new BlockChainValidationResult(false, i, ValidationResultType.INVALID_HASH);
			}
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				return new BlockChainValidationResult(false, i, ValidationResultType.INVALID_PREVIOUS_HASH);
			}
			if (difficulty > 0) {
				if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
					return new BlockChainValidationResult(false, i, ValidationResultType.NOT_MINED);
				}
			}

		}
		return new BlockChainValidationResult(true, -1, null);
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getBlockPos(Block block) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i) == block) {
				return i;
			}
		}
		return -1;
	}

}
