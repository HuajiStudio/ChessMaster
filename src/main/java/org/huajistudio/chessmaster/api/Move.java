package org.huajistudio.chessmaster.api;

public class Move {
	public BoardPos origin;
	public BoardPos end;
	public ChessObject turned;
	public Board board;

	public Move(BoardPos origin, BoardPos end, ChessObject turned, Board board) {
		this.origin = origin;
		this.end = end;
		this.turned = turned;
		this.board = board;
	}
}
