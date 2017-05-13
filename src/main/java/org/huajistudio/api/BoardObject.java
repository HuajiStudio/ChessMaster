package org.huajistudio.api;

import java.util.Arrays;

public class BoardObject {
	private ChessObject[] chessObjects = {};

	public ChessObject[] getChessObjects() {
		return chessObjects;
	}

	public void setChessObjects(ChessObject[] chessObjects) {
		this.chessObjects = chessObjects;
	}

	@Override
	public String toString() {
		return "BoardObject{" + Arrays.toString(chessObjects) + "}";
	}
}
