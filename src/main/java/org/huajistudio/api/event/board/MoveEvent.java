package org.huajistudio.api.event.board;

import org.huajistudio.api.Move;

public class MoveEvent extends BoardEvent {
	private Move move;

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
}
