package org.huajistudio.chessmaster.api.event.board;

import org.huajistudio.chessmaster.api.Board;

import java.io.File;

public class BoardReadEvent extends BoardIOEvent {
	public BoardReadEvent(Board board, File boardFile) {
		super(board, boardFile);
	}
}
