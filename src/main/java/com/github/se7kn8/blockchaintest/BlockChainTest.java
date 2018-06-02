package com.github.se7kn8.blockchaintest;

public class BlockChainTest {

	public static void main(String[] args) {
		BlockChain chain = new BlockChain("The first block", 5);
		System.out.println("Start generating blocks");
		for (int i = 0; i < 10; i++) {
			chain.addBlock("Block: " + i);
			System.out.println("Block added");
		}
		System.out.println("End generating blocks");
		for (int i = 0; i < chain.getBlocks().size(); i++) {
			System.out.println("Block " + i + " " + chain.getBlocks().get(i).getData() + " " + chain.getBlocks().get(i).getHash());
		}

		if (chain.isChainValid()) {
			System.out.println("Chain is valid");
		}
	}

}
