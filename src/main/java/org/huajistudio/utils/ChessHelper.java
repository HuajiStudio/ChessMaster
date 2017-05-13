package org.huajistudio.utils;

import org.huajistudio.api.*;
import org.huajistudio.api.math.ChessVector;

public interface ChessHelper {

	boolean BLACK = true;
	boolean WHITE = false;

	static boolean canMove(Move move) {
		return canMoveTo(move.turned, move.end, move.board);
	}

    static boolean canMoveTo(ChessObject chess, BoardPos pos, Board board) {
    	ChessObject chessObjectAtDestination = board.getChess(pos);
    	if (chessObjectAtDestination != null)
			if (chessObjectAtDestination.side == chess.side)
				return false;

		for (ChessVector vector : chess.type.getMovableVectors()) {
    		switch (vector.getDirection()) {
				case UP:
					if (pos.getY() - chess.pos.getY() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case DOWN:
					if (chess.pos.getY() - pos.getY() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case LEFT:
					if (chess.pos.getX() - pos.getX() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case RIGHT:
					if (pos.getX() - chess.pos.getX() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case UP_LEFT:
					if (pos.getY() - chess.pos.getY() != vector.getSize() || chess.pos.getX() - pos.getX() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case UP_RIGHT:
					if (pos.getY() - chess.pos.getY() != vector.getSize() || pos.getX() - chess.pos.getX() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case DOWN_LEFT:
					if (chess.pos.getY() - pos.getY() != vector.getSize() || chess.pos.getX() - pos.getX() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
				case DOWN_RIGHT:
					if (chess.pos.getY() - pos.getY() != vector.getSize() || pos.getX() - chess.pos.getX() != vector.getSize() || vector.getSize() != Integer.MAX_VALUE)
						return false;
					break;
			}
		}

		return true;
    }
}
