package org.huajistudio.api.event.chess;

import org.huajistudio.api.Board;
import org.huajistudio.api.Move;
import org.huajistudio.api.event.board.BoardEvent;

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
