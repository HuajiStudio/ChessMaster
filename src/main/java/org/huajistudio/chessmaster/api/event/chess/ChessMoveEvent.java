package org.huajistudio.chessmaster.api.event.chess;

import org.huajistudio.chessmaster.api.Board;
import org.huajistudio.chessmaster.api.Move;
import org.huajistudio.chessmaster.api.event.board.BoardEvent;

public class ChessMoveEvent extends BoardEvent {
	private Move move;

	public ChessMoveEvent(Board board, Move move) {
		super(board);
		this.move = move;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
}
