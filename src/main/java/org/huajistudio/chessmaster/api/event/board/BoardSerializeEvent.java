package org.huajistudio.chessmaster.api.event.board;

import org.huajistudio.chessmaster.api.Board;

public class BoardSerializeEvent extends BoardEvent {
	public BoardSerializeEvent(Board board) {
		super(board);
	}
}
