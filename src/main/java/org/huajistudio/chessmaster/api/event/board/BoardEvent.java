package org.huajistudio.chessmaster.api.event.board;

import org.huajistudio.chessmaster.api.Board;
import org.huajistudio.chessmaster.api.event.CancelableEvent;

public class BoardEvent extends CancelableEvent {
	private Board board;

	public BoardEvent(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
