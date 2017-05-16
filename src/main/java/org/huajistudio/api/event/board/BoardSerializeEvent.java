package org.huajistudio.api.event.board;

import org.huajistudio.api.Board;

public class BoardSerializeEvent extends BoardEvent {
	public BoardSerializeEvent(Board board) {
		super(board);
	}
}
