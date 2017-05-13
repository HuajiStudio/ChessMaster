package org.huajistudio.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board implements Iterable<ChessObject> {
	public Map<BoardPos, ChessObject> chessObjects;

	public class BoardPos {
		public int x, y;

		@Override
		public boolean equals(Object obj) {
			return obj instanceof BoardPos && ((BoardPos) obj).x == x && ((BoardPos) obj).y == y;
		}

		@Override
		public String toString() {
			return "BoardPos{x=" + x + ", y=" + y + "}";
		}
	}

	public Board() {
		this.chessObjects = new HashMap<>();
	}

	@Override
	public Iterator<ChessObject> iterator() {
		return chessObjects.values().iterator();
	}
}
