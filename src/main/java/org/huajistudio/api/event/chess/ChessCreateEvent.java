package org.huajistudio.api.event.chess;

import org.huajistudio.api.ChessObject;

public class ChessCreateEvent extends ChessEvent {
	public ChessCreateEvent(ChessObject chessObject) {
		super(chessObject);
	}
}
