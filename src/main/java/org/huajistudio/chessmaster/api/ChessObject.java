package org.huajistudio.chessmaster.api;

public class ChessObject {
	private BoardPos pos;
	private Chess type;
	private boolean side;
	/* for judging special moves */
	private boolean moved = false;

	public ChessObject(BoardPos pos, Chess type, boolean side) {
		this.pos = pos;
		this.type = type;
		this.side = side;
	}

	public BoardPos getPos() {
		return pos;
	}

	public void setPos(BoardPos pos) {
		this.pos = pos;
	}

	public Chess getType() {
		return type;
	}

	public void setType(Chess type) {
		this.type = type;
	}

	public boolean getSide() {
		return side;
	}

	public void setSide(boolean side) {
		this.side = side;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	@Override
	public String toString() {
		return "ChessObject{pos=" +
			pos + ", type=" +
			type + ", side=" +
			side + "}";
	}
}
