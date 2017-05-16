package org.huajistudio.api.event.board;

import org.huajistudio.api.Board;

import java.io.File;

public class BoardWriteEvent extends BoardIOEvent {
	public BoardWriteEvent(Board board, File boardFile) {
		super(board, boardFile);
	}
}
