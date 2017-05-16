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
			if (chessObjectAtDestination.getSide() == chess.getSide())
				return false;

		int deltaX = pos.getX() - chess.getPos().getX();
	    int deltaY = pos.getY() - chess.getPos().getY();

	    for (ChessVector vector : chess.getType().getMovableVectors()) {
	    	if (deltaX != 0 && vector.getDirection().getOffsetX() != 0) {
			    if (deltaX % vector.getDirection().getOffsetX() != 0)
				    continue;
			    int s = deltaX / vector.getDirection().getOffsetX();
			    if (s * deltaY == vector.getDirection().getOffsetY())
				    return true;
		    } else if (deltaY != 0 && vector.getDirection().getOffsetY() != 0) {
		    	if (deltaY % vector.getDirection().getOffsetY() != 0)
		    		continue;
			    int s = deltaY / vector.getDirection().getOffsetY();
			    if (s * deltaX == vector.getDirection().getOffsetX())
			    	return true;
		    }
	    }

	    if (chess.getType() == Chess.PAWN)
	    	if (chess.getSide() == WHITE) if (!chess.isMoved())
			    return chess.getPos().move(0, 1).equals(pos) || chess.getPos().move(0, 2).equals(pos);
		    else
			    return chess.getPos().move(0, 1).equals(pos);
		    else if (!chess.isMoved())
			    return chess.getPos().move(0, -1).equals(pos) || chess.getPos().move(0, -2).equals(pos);
		    else
			    return chess.getPos().move(0, -1).equals(pos);

		return false;
    }
}
