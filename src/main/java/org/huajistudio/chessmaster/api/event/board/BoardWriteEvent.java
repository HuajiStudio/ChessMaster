package org.huajistudio.chessmaster.api.event.board;

import org.huajistudio.chessmaster.api.Board;

import java.io.File;

public class BoardWriteEvent extends BoardIOEvent {
	public BoardWriteEvent(Board board, File boardFile) {
		super(board, boardFile);
	}
}
