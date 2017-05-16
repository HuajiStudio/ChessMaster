package org.huajistudio.api.event.chess;

import org.huajistudio.api.ChessObject;
import org.huajistudio.api.event.CancelableEvent;

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
