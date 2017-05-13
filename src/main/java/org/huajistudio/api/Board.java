package org.huajistudio.api;

import java.util.Map;

public class Board {
	public Map<BoardPos, ChessObject> chessObjects;

	public class BoardPos {
		public int x, y;

		@Override
		public boolean equals(Object obj) {
			return obj instanceof BoardPos && ((BoardPos) obj).x == x && ((BoardPos) obj).y == y;
		}
	}
}
