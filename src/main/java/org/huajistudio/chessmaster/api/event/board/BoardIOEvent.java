package org.huajistudio.chessmaster.api.event.board;

import org.huajistudio.chessmaster.api.Board;

import java.io.File;

public class BoardIOEvent extends BoardEvent {
	private File boardFile;

	public BoardIOEvent(Board board, File boardFile) {
		super(board);
		this.boardFile = boardFile;
	}

	public File getBoardFile() {
		return boardFile;
	}

	public void setBoardFile(File boardFile) {
		this.boardFile = boardFile;
	}
}
