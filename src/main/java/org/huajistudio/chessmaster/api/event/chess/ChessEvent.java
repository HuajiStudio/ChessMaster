package org.huajistudio.chessmaster.api.event.chess;

import org.huajistudio.chessmaster.api.ChessObject;
import org.huajistudio.chessmaster.api.event.CancelableEvent;

public class ChessEvent extends CancelableEvent {
	private ChessObject chessObject;

	public ChessEvent(ChessObject chessObject) {
		this.chessObject = chessObject;
	}

	public ChessObject getChessObject() {
		return chessObject;
	}

	public void setChessObject(ChessObject chessObject) {
		this.chessObject = chessObject;
	}
}
