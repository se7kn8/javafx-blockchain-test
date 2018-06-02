package com.github.se7kn8.blockchaintest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BlockChainTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/BlockChainTest.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(BlockChainTest.class, args);
		// BlockChain chain = new BlockChain("The first block", 5);
		// System.out.println("Start generating blocks");
		// for (int i = 0; i < 10; i++) {
		// 	 chain.addBlock("Block: " + i);
		// 	 System.out.println("Block added");
		// }
		// System.out.println("End generating blocks");
		// for (int i = 0; i < chain.getBlocks().size(); i++) {
		//	 System.out.println("Block " + i + " " + chain.getBlocks().get(i).getData() + " " + chain.getBlocks().get(i).getHash());
		// }

		// if (chain.isChainValid()) {
		//	 System.out.println("Chain is valid");
		// }
	}

}
