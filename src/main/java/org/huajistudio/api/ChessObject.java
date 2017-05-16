package org.huajistudio.api;

public class ChessObject {
	public BoardPos pos;
	public Chess type;
	public boolean side;
	/* for judging special moves */
	public boolean moved = false;

	public ChessObject(BoardPos pos, Chess type, boolean side) {
		this.pos = pos;
		this.type = type;
		this.side = side;
	}

	@Override
	public String toString() {
		return "ChessObject{pos=" +
			pos + ", type=" +
			type + ", side=" +
			side + "}";
	}
}
