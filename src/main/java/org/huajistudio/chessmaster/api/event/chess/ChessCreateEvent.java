package org.huajistudio.chessmaster.api.event.chess;

import org.huajistudio.chessmaster.api.ChessObject;

public class ChessCreateEvent extends ChessEvent {
	public ChessCreateEvent(ChessObject chessObject) {
		super(chessObject);
	}
}
