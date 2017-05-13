package org.huajistudio.api;

public class ChessObject {
	public BoardPos pos;
	public Chess type;
	public boolean side;

	@Override
	public String toString() {
		return "ChessObject{pos=" +
			pos + ", type=" +
			type + ", side=" +
			side + "}";
	}
}
